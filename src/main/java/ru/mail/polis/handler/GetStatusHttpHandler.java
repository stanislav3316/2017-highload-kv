package ru.mail.polis.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by iters on 9/29/17.
 */
public class GetStatusHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (!httpExchange.getRequestMethod().equalsIgnoreCase("GET")) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0);
        }

        httpExchange.close();
    }
}