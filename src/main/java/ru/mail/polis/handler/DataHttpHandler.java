package ru.mail.polis.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.apache.http.HttpResponse;
import ru.mail.polis.util.HttpClientUtils;
import ru.mail.polis.util.ServerSelectorUtils;
import ru.mail.polis.util.StorageDataUtils;
import ru.mail.polis.util.StringHashingUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static java.net.HttpURLConnection.*;

/**
 * Created by iters on 9/29/17.
 */
public class DataHttpHandler implements HttpHandler {
    private final File path;
    private final Set<String> topology;
    private final int serverPort;
    private Set<String> deletedKeys = new HashSet<>();

    public DataHttpHandler(File path, Set<String> topology, int serverPort) {
        this.path = path;
        this.topology = topology;
        this.serverPort = serverPort;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        if (httpExchange.getRequestURI().getQuery() == null
                || httpExchange.getRequestURI().getQuery().equalsIgnoreCase("id=")) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            httpExchange.close();
            return;
        }

        if (topology.size() == 1) {
            deletedKeys.clear();
        }

        String query = httpExchange.getRequestURI().getQuery();
        if (!query.contains("&locally")
                && !query.contains("&replicas")
                && topology.size() > 1) {
            query += "&replicas=" + (topology.size() / 2 + 1) + "/" + topology.size();
        } else if (query.contains("&locally")
                && topology.size() > 1) {
            query = query.substring(0, query.indexOf("&locally"));
        }

        switch (httpExchange.getRequestMethod()) {
            case "GET":
                if (query.contains("&replicas")) {
                    handleGETMethodWithTopology(httpExchange, query);
                } else {
                    handleGETMethod(httpExchange, query);
                }

                break;
            case "PUT":
                if (query.contains("&replicas")) {
                    handlePUTMethodWithTopology(httpExchange, query);
                } else {
                    handlePUTMethod(httpExchange, query);
                }

                break;
            case "DELETE":
                if (query.contains("&replicas")) {
                    handleDELETEMethodWithTopology(httpExchange, query);
                } else {
                    handleDELETEMethod(httpExchange, query);
                }

                break;
        }

        httpExchange.close();
    }

    private void handleGETMethod(HttpExchange httpExchange,
                                 String query) throws IOException {
        String key = query.split("id=")[1];
        byte[] data = StorageDataUtils.getDataFromStorage(key, path);

        if (data == null && !deletedKeys.contains(key)) {
            httpExchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
        } else if (data == null && deletedKeys.contains(key)) {
            httpExchange.sendResponseHeaders(HTTP_CONFLICT, 0);
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0);
            httpExchange.getResponseBody().write(data);
        }
    }

    private void handleGETMethodWithTopology(HttpExchange httpExchange,
                                             String query) throws IOException {
        //TODO: выделить код ниже в utils класс

        String[] twoPartition = query.split("&");
        String tempReplicates = twoPartition[1].split("=")[1];

        String key = twoPartition[0].substring(
                twoPartition[0].indexOf("=") + 1, twoPartition[0].length());
        int asc = Integer.parseInt(tempReplicates.split("/")[0]);
        int from = Integer.parseInt(tempReplicates.split("/")[1]);

        Set<String> servers = ServerSelectorUtils.getServers(topology,
                StringHashingUtils.getSimpleHash(topology.size(), key), from);

        // check
        if (asc == 0 || from == 0 || asc > from) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            httpExchange.close();
            return;
        }

        int successGetting = 0;
        InputStream is = null;
        for (String server : servers) {
            if (server.contains(":" + serverPort)) {
                byte[] data = StorageDataUtils.getDataFromStorage(key, path);

                if (data != null) {
                    is = new ByteArrayInputStream(data);
                }

                successGetting++;
                continue;
            }

            HttpResponse resp = null;

            try {
                resp = HttpClientUtils.get(key, server);
            } catch (IOException e) {
                continue;
            }

            int code = resp.getStatusLine().getStatusCode();

            if (code == HTTP_OK) {
                successGetting++;
                is = resp.getEntity().getContent();
            } else if (code == HTTP_CONFLICT) {
                httpExchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
                return;
            }

        }

        if (successGetting >= asc) {
            byte[] data = new byte[is.available()];
            is.read(data);
            is.close();
            httpExchange.sendResponseHeaders(HTTP_OK, 0);
            httpExchange.getResponseBody().write(data);
        } else if (successGetting == 0) {
            httpExchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
        } else {
            httpExchange.sendResponseHeaders(HTTP_GATEWAY_TIMEOUT, 0);
        }

    }

    private void handlePUTMethod(HttpExchange httpExchange,
                                 String query) throws IOException {
        String key = query.split("id=")[1];
        InputStream is = httpExchange.getRequestBody();
        byte[] bytes = new byte[is.available()];

        is.read(bytes, 0, bytes.length);
        is.close();
        deletedKeys.remove(key);

        StorageDataUtils.createOrUpdateFiles(key, bytes, path);
        httpExchange.sendResponseHeaders(HTTP_CREATED, 0);
    }

    private void handlePUTMethodWithTopology(HttpExchange httpExchange,
                                             String query) throws IOException {
        String[] twoPartition = query.split("&");
        String tempReplicates = twoPartition[1].split("=")[1];

        String key = twoPartition[0].substring(
                twoPartition[0].indexOf("=") + 1, twoPartition[0].length());
        int asc = Integer.parseInt(tempReplicates.split("/")[0]);
        int from = Integer.parseInt(tempReplicates.split("/")[1]);

        Set<String> servers = ServerSelectorUtils.getServers(topology,
                StringHashingUtils.getSimpleHash(topology.size(), key), from);

        // check
        if (asc == 0 || from == 0 || asc > from) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            httpExchange.close();
            return;
        }

        int successPutting = 0;
        InputStream is = httpExchange.getRequestBody();
        byte[] bytes = new byte[is.available()];
        is.read(bytes, 0, bytes.length);
        is.close();

        for (String server : servers) {
            if (server.contains(":" + serverPort)) {
                StorageDataUtils.createOrUpdateFiles(key, bytes, path);
                deletedKeys.remove(key);
                successPutting++;
                continue;
            }

            HttpResponse resp = null;

            try {
                resp = HttpClientUtils.put(key, server, bytes);
            } catch (IOException e) {
                continue;
            }

            int code = resp.getStatusLine().getStatusCode();

            if (code == HTTP_CREATED) {
                successPutting++;
            }
        }

        if (successPutting >= asc) {
            httpExchange.sendResponseHeaders(HTTP_CREATED, 0);
        } else {
            httpExchange.sendResponseHeaders(HTTP_GATEWAY_TIMEOUT, 0);
        }

    }

    private void handleDELETEMethod(HttpExchange httpExchange,
                                    String query) throws IOException {
        String key = query.split("id=")[1];

        File file = new File(path + File.separator + key);
        if (file.exists()) {
            ru.mail.polis.Files.deleteFileByName(path.toString(), key);
            deletedKeys.add(key);
        }

        httpExchange.sendResponseHeaders(HTTP_ACCEPTED, 0);
    }

    private void handleDELETEMethodWithTopology(HttpExchange httpExchange,
                                                String query) throws IOException {
        String[] twoPartition = query.split("&");
        String tempReplicates = twoPartition[1].split("=")[1];

        String key = twoPartition[0].substring(
                twoPartition[0].indexOf("=") + 1, twoPartition[0].length());
        int asc = Integer.parseInt(tempReplicates.split("/")[0]);
        int from = Integer.parseInt(tempReplicates.split("/")[1]);

        Set<String> servers = ServerSelectorUtils.getServers(topology,
                StringHashingUtils.getSimpleHash(topology.size(), key), from);

        // check
        if (asc == 0 || from == 0 || asc > from) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            httpExchange.close();
            return;
        }

        int successDeleting = 0;
        for (String server : servers) {

            if (server.contains(":" + serverPort)) {
                File file = new File(path + File.separator + key);
                if (file.exists()) {
                    ru.mail.polis.Files.deleteFileByName(path.toString(), key);
                    deletedKeys.add(key);
                }

                successDeleting++;
                continue;
            }

            HttpResponse resp = null;

            try {
                resp = HttpClientUtils.delete(key, server);
            } catch (IOException e) {
                continue;
            }

            int code = resp.getStatusLine().getStatusCode();

            if (code == HTTP_ACCEPTED) {
                successDeleting++;
            }
        }

        if (successDeleting >= asc) {
            httpExchange.sendResponseHeaders(HTTP_ACCEPTED, 0);
        } else {
            httpExchange.sendResponseHeaders(HTTP_GATEWAY_TIMEOUT, 0);
        }
    }

}