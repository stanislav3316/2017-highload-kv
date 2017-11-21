package ru.mail.polis.httpClient;

import one.nio.http.HttpClient;
import one.nio.http.HttpException;
import one.nio.http.Response;
import one.nio.net.ConnectionString;
import one.nio.pool.PoolException;

import java.io.IOException;

/**
 * Created by iters on 11/20/17.
 */
public class OneNioHttpClient implements BasicHttpClient<Response, Response, Response> {

    private static final String URL_PATH = "/v0/inner?id=";

    @Override
    public Response sendGet(String server, String id) throws IOException, InterruptedException, HttpException, PoolException {
        HttpClient client = new HttpClient(new ConnectionString(server));
        return client.get(URL_PATH + id);
    }

    @Override
    public Response sendPut(String server, String id, byte[] body) throws IOException, InterruptedException, HttpException, PoolException {
        HttpClient client = new HttpClient(new ConnectionString(server));
        return client.put(URL_PATH + id, body);
    }

    @Override
    public Response sendDelete(String server, String id) throws IOException, InterruptedException, HttpException, PoolException {
        HttpClient client = new HttpClient(new ConnectionString(server));
        return client.delete(URL_PATH + id);
    }
}
