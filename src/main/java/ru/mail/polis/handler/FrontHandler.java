package ru.mail.polis.handler;

import one.nio.http.*;
import ru.mail.polis.httpClient.BasicHttpClient;
import ru.mail.polis.httpClient.OneNioHttpClient;
import ru.mail.polis.message.RequestParameters;
import ru.mail.polis.message.ResponseMessage;
import ru.mail.polis.storageManager.BasicStorage;
import ru.mail.polis.util.ServerSelectorUtils;
import ru.mail.polis.util.StringHashingUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by iters on 11/17/17.
 */
public class FrontHandler {
    private List<String> topology;
    private BasicHttpClient client;
    private final int port;
    private final BasicStorage storage;
    private final CompletionService<ResponseMessage> ecs;

    private final int OK_CODE = 200;
    private final int DELETED_CODE = 204;
    private final int CREATED_CODE = 201;
    private final int ACCEPTED_CODE = 202;
    private final int NOT_FOUND_CODE = 404;

    public FrontHandler(Set<String> topology, int port, BasicStorage storage) {
        this.topology = new ArrayList<>(topology);
        this.port = port;
        this.storage = storage;
        client = new OneNioHttpClient(this.topology);
        ecs = new ExecutorCompletionService<>(
                Executors.newCachedThreadPool()
        );
    }

    @Path("/v0/status")
    public Response statusHandler() {
        return Response.ok("service is ready");
    }
    
    @Path("/v0/entity")
    public void handler(Request request,
                        HttpSession session,
                        @Param(value = "id") String id,
                        @Param(value = "replicas") String replicas) throws IOException, ExecutionException, InterruptedException {

        if (id == null || id.length() == 0) {
            session.sendError(Response.BAD_REQUEST, "bad request");
            return;
        }

        RequestParameters reqPar =
                RequestParameters.getRequetInfoInstance(replicas, topology.size());

        if (reqPar == null) {
            session.sendError(Response.BAD_REQUEST, "Bad params");
            return;
        }

        switch (request.getMethod()) {
            case Request.METHOD_GET:
                getController(session, id, reqPar.getAck(), reqPar.getFrom());
                break;
            case Request.METHOD_PUT:
                putController(request, session, id, reqPar.getAck(), reqPar.getFrom());
                break;
            case Request.METHOD_DELETE:
                deleteController(session, id, reqPar.getAck(), reqPar.getFrom());
                break;
            default:
                session.sendError(Response.BAD_REQUEST, "Not available HTTP Method");
        }
    }

    private void getController(HttpSession session,
                               String id,
                               int ack,
                               int from) throws IOException, InterruptedException, ExecutionException {
        int received = 0;
        int notFound = 0;
        int activeTasks = 0;
        byte[] data = null;

        for (String server : ServerSelectorUtils.getServers(topology, id, from)) {
            if (server.endsWith(port + "")) {
                if (storage.isDeleted(id)) {
                    session.sendResponse(new Response(
                            Response.NOT_FOUND, "file dot found".getBytes()));
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

            ecs.submit(() -> {
                try {
                    Response resp = (Response) client.sendGet(server, id);
                    ResponseMessage resMes;

                    if (resp.getStatus() == OK_CODE) {
                        resMes = new ResponseMessage(resp.getStatus(), resp.getBody());
                    } else {
                        resMes = new ResponseMessage(resp.getStatus());
                    }

                    return resMes;
                } catch (Exception e) {
                    return null;
                }
            });

            activeTasks++;
        }

        for (int i = 0; i < activeTasks; i++) {
            ResponseMessage rm =  ecs.take().get();

            if (rm == null) {
                continue;
            }

            switch (rm.code) {
                case OK_CODE:
                    received++;
                    data = (data == null) ? rm.getData() : data;

                    // missing write
                    if (!storage.isDataExist(id)) {
                        storage.saveData(id, data);
                        received++;
                    }

                    break;
                case DELETED_CODE:
                    session.sendResponse(new Response(
                            Response.NOT_FOUND, "file not found".getBytes()));
                    return;
                case NOT_FOUND_CODE:
                    notFound++;
                    break;
            }
        }

        if (received >= ack) {
            session.sendResponse(new Response(Response.OK, data));
        } else if (notFound + received < ack) {
            session.sendResponse(new Response(
                    Response.GATEWAY_TIMEOUT, "not enought replicas".getBytes()));
        } else if (received == 0) {
            session.sendResponse(new Response(
                    Response.NOT_FOUND, "file not found".getBytes()));
        }

    }

    private void putController(Request request,
                               HttpSession session,
                               String id,
                               int ack,
                               int from) throws IOException, InterruptedException, ExecutionException {
        int received = 0;
        int activeTasks = 0;

        for (String server : ServerSelectorUtils.getServers(topology, id, from)) {
            if (server.contains(port + "")) {
                byte[] body = request.getBody();
                boolean isPutted = storage.saveData(id, body);

                received++;
                continue;
            }

            ecs.submit(() -> {
                try {
                    Response resp = (Response) client.sendPut(server, id, request.getBody());
                    return new ResponseMessage(resp.getStatus());
                } catch (Exception e) {
                    return null;
                }
            });

            activeTasks++;
        }

        for (int i = 0; i < activeTasks; i++) {
            ResponseMessage rm =  ecs.take().get();

            if (rm == null) {
                continue;
            }

            System.out.println(rm.code);
            if (rm.code == CREATED_CODE) {
                received++;
            }
        }

        if (received >= ack) {
            session.sendResponse(new Response(
                    Response.CREATED, "file was created".getBytes()));
        } else {
            session.sendResponse(new Response(
                    Response.GATEWAY_TIMEOUT, "not enought replicas".getBytes()));
        }
    }

    private void deleteController(HttpSession session,
                                  String id,
                                  int ack,
                                  int from) throws IOException, InterruptedException, ExecutionException {
        int received = 0;
        int activeTasks = 0;

        for (String server : ServerSelectorUtils.getServers(topology, id, from)) {
            if (server.endsWith(port + "")) {
                storage.removeData(id);
                received++;
                continue;
            }

            ecs.submit(() -> {
                try {
                    Response resp = (Response) client.sendDelete(server, id);
                    return new ResponseMessage(resp.getStatus());
                } catch (Exception e) {
                    return null;
                }
            });

            activeTasks++;
        }

        for (int i = 0; i < activeTasks; i++) {
            ResponseMessage rm =  ecs.take().get();

            if (rm == null) {
                continue;
            }

            if (rm.code == ACCEPTED_CODE) {
                received++;
            }
        }

        if (received >= ack) {
            session.sendResponse(new Response(
                    Response.ACCEPTED, "file was deleted".getBytes()));
        } else {
            session.sendResponse(new Response(
                    Response.GATEWAY_TIMEOUT, "not enought replicas".getBytes()));
        }
    }
}
