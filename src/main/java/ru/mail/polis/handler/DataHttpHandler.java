package ru.mail.polis.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sun.misc.IOUtils;

import java.io.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.net.HttpURLConnection.*;

/**
 * Created by iters on 9/29/17.
 */
public class DataHttpHandler implements HttpHandler {
    private final File path;

    public DataHttpHandler(File path) {
        this.path = path;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (httpExchange.getRequestURI().getQuery() == null
                || httpExchange.getRequestURI().getQuery().equalsIgnoreCase("id=")) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
            httpExchange.close();
            return;
        }

        switch (httpExchange.getRequestMethod()) {
            case "GET":
                handleGETMethod(httpExchange);
                break;
            case "PUT":
                handlePUTMethod(httpExchange);
                break;
            case "DELETE":
                handleDELETEMethod(httpExchange);
        }

        httpExchange.close();
    }

    private void handleGETMethod(HttpExchange httpExchange) throws IOException {
        String key = httpExchange.getRequestURI().getQuery().split("id=")[1];
        byte[] data = getDataFromStorage(key);

        if (data == null) {
            httpExchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0);
            httpExchange.getResponseBody().write(data);
        }
    }

    private void handlePUTMethod(HttpExchange httpExchange) throws IOException {
        String key = httpExchange.getRequestURI().getQuery().split("id=")[1];
        InputStream is = httpExchange.getRequestBody();
        byte[] bytes = new byte[is.available()];

        is.read(bytes, 0, bytes.length);
        is.close();

        createOrUpdateFiles(key, bytes);
        httpExchange.sendResponseHeaders(HTTP_CREATED, 0);
    }

    private void handleDELETEMethod(HttpExchange httpExchange) throws IOException {
        String key = httpExchange.getRequestURI().getQuery().split("id=")[1];
        File file = new File(path + File.pathSeparator + key);
        file.delete();
        httpExchange.sendResponseHeaders(HTTP_ACCEPTED, 0);
    }

    private byte[] getDataFromStorage(String key) {
        File storage = new File(path + File.separator + key);

        if (!storage.exists()) {
            return null;
        }

        try {
            return IOUtils.readFully(new FileInputStream(storage), -1, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createOrUpdateFiles(String key, byte[] data) throws UnsupportedEncodingException {
        File storage = new File(path + File.separator + key);

        if(storage.exists()) {
            storage.delete();
        }


        try {
            java.nio.file.Files.write(
                    Paths.get(storage.toURI()),
                    data,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}