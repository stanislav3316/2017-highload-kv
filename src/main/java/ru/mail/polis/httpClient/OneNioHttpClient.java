package ru.mail.polis.httpClient;

import one.nio.http.HttpClient;
import one.nio.http.HttpException;
import one.nio.http.Response;
import one.nio.net.ConnectionString;
import one.nio.pool.PoolException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iters on 11/20/17.
 */
public class OneNioHttpClient implements BasicHttpClient<Response, Response, Response> {

    private static final String GET_PATH = "/v0/get?id=";
    private static final String PUT_PATH = "/v0/put?id=";
    private static final String DELETE_PATH = "/v0/delete?id=";

    @Override
    public Response sendGet(String server, String id) throws IOException, InterruptedException, HttpException, PoolException {
        HttpClient client = new HttpClient(new ConnectionString(server));
        return client.get(GET_PATH + id);
    }

    @Override
    public Response sendPut(String server, String id, byte[] body) throws IOException, InterruptedException, HttpException, PoolException {
        HttpClient client = new HttpClient(new ConnectionString(server));
        return client.put(PUT_PATH + id, body);
    }

    @Override
    public Response sendPutTTL(String server, String id, byte[] body, long ttl) throws IOException, InterruptedException, HttpException, PoolException {
        HttpClient client = new HttpClient(new ConnectionString(server));
        return client.put(PUT_PATH + id + "&ttl=" + ttl, body);
    }

    @Override
    public Response sendDelete(String server, String id) throws IOException, InterruptedException, HttpException, PoolException {
        HttpClient client = new HttpClient(new ConnectionString(server));
        return client.delete(DELETE_PATH + id);
    }
}
