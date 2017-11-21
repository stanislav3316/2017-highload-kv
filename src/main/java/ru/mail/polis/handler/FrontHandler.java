package ru.mail.polis.handler;

import one.nio.http.*;
import ru.mail.polis.httpClient.BasicHttpClient;
import ru.mail.polis.httpClient.OneNioHttpClient;
import ru.mail.polis.storageManager.BasicStorage;
import ru.mail.polis.util.ServerSelectorUtils;
import ru.mail.polis.util.StringHashingUtils;
import java.io.IOException;
import java.util.Set;

/**
 * Created by iters on 11/17/17.
 */
public class FrontHandler {
    private Set<String> topology;
    private BasicHttpClient client;
    private final int port;

    private BasicStorage storage;
    private Set<String> deletedKeys;

    private final int OK_CODE = 200;
    private final int DELETED_CODE = 204;
    private final int CREATED_CODE = 201;
    private final int ACCEPTED_CODE = 202;
    private final int NOT_FOUND_CODE = 404;

    public FrontHandler(Set<String> topology, int port, BasicStorage storage, Set<String> deletedKeys) {
        this.topology = topology;
        this.port = port;
        this.storage = storage;
        this.deletedKeys = deletedKeys;
        client = new OneNioHttpClient();
    }

    @Path("/v0/status")
    public Response statusHandler() {
        return Response.ok("service is ready");
    }
    
    @Path("/v0/entity")
    public void handler(Request request,
                        HttpSession session,
                        @Param(value = "id") String id,
                        @Param(value = "replicas") String replicas) throws IOException {

        if (id == null || id.length() == 0) {
            session.sendError(Response.BAD_REQUEST, "bad request");
            return;
        }

        replicas = (replicas == null || replicas.length() == 0) ?
                (topology.size() / 2 + 1) + "/" + topology.size() :
                replicas;

        int ack = Integer.parseInt(replicas.split("/")[0]);
        int from = Integer.parseInt(replicas.split("/")[1]);

        if (ack == 0 || from == 0 || ack > from) {
            session.sendError(Response.BAD_REQUEST, "Bad params");
            return;
        }

        switch (request.getMethod()) {
            case Request.METHOD_GET:
                getController(session, id, ack, from);
                break;
            case Request.METHOD_PUT:
                putController(request, session, id, ack, from);
                break;
            case Request.METHOD_DELETE:
                deleteController(session, id, ack, from);
                break;
            default:
                session.sendError(Response.BAD_REQUEST, "Not available HTTP Method");
        }
    }

    private void getController(HttpSession session,
                               String id,
                               int ack,
                               int from) throws IOException {
        long hashId = StringHashingUtils.getSimpleHash(topology.size(), id);
        Set<String> dataTopology = ServerSelectorUtils.getServers(topology, hashId, from);

        int received = 0;
        int notFound = 0;
        byte[] data = null;

        for (String server : dataTopology) {

            if (server.endsWith(port + "")) {
                if (deletedKeys.contains(id)) {
                    session.sendResponse(new Response(Response.NOT_FOUND, "file does't found".getBytes()));
                    return;
                }

                if (!storage.isDataExist(id)) {
                    notFound++;
                } else {
                    data = storage.getData(id);
                    received++;
                }

                continue;
            }

            Response resp = null;

            try {
                resp = (Response) client.sendGet(server, id);
            } catch (Exception e) {
                continue;
            }

            if (resp.getStatus() == OK_CODE) {
                received++;
                data = resp.getBody();
            } else if (resp.getStatus() == DELETED_CODE) {
                session.sendResponse(new Response(Response.NOT_FOUND, "file not found".getBytes()));
                return;
            } else if (resp.getStatus() == NOT_FOUND_CODE) {
                notFound++;
            }
        }

        if (notFound + received < ack) {
            session.sendResponse(new Response(Response.GATEWAY_TIMEOUT, "not enought repicas".getBytes()));
        } else if (received == 0) {
            session.sendResponse(new Response(Response.NOT_FOUND, "file not found".getBytes()));
        } else {
            session.sendResponse(new Response(Response.OK, data));
        }
    }

    private void putController(Request request,
                               HttpSession session,
                               String id,
                               int ack,
                               int from) throws IOException {
        long hashId = StringHashingUtils.getSimpleHash(topology.size(), id);
        Set<String> dataTopology = ServerSelectorUtils.getServers(topology, hashId, from);

        int received = 0;

        for (String server : dataTopology) {
            if (server.contains(port + "")) {
                byte[] body = request.getBody();
                boolean isPutted = storage.saveData(id, body);

                if (deletedKeys.contains(id)) {
                    deletedKeys.remove(id);
                }

                received++;
                continue;
            }

            Response resp = null;

            try {
                resp = (Response) client.sendPut(server, id, request.getBody());
            } catch (Exception e) {
                continue;
            }

            if (resp.getStatus() == CREATED_CODE) {
                received++;
            }
        }

        if (received >= ack) {
            session.sendResponse(new Response(Response.CREATED, "file was created".getBytes()));
        } else {
            session.sendResponse(new Response(Response.GATEWAY_TIMEOUT, "not enought replicas".getBytes()));
        }
    }

    private void deleteController(HttpSession session,
                                  String id,
                                  int ack,
                                  int from) throws IOException {
        long hashId = StringHashingUtils.getSimpleHash(topology.size(), id);
        Set<String> dataTopology = ServerSelectorUtils.getServers(topology, hashId, from);

        int received = 0;

        for (String server : dataTopology) {
            if (server.endsWith(port + "")) {
                storage.removeData(id);
                deletedKeys.add(id);

                received++;
                continue;
            }

            Response resp = null;

            try {
                resp = (Response) client.sendDelete(server, id);
            } catch (Exception e) {
                continue;
            }

            if (resp.getStatus() == ACCEPTED_CODE) {
                received++;
            }
        }

        if (received >= ack) {
            session.sendResponse(new Response(Response.ACCEPTED, "file was deleted".getBytes()));
        } else {
            session.sendResponse(new Response(Response.GATEWAY_TIMEOUT, "not enought replicas".getBytes()));
        }
    }
}
