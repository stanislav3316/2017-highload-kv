package ru.mail.polis.httpClient;

import one.nio.http.HttpException;
import one.nio.pool.PoolException;

import java.io.IOException;

/**
 * Created by iters on 11/17/17.
 */
public interface BasicHttpClient<G, P, D> {
    G sendGet(String server, String id) throws IOException, InterruptedException, HttpException, PoolException;
    P sendPut(String server, String id, byte[] body) throws IOException, InterruptedException, HttpException, PoolException;
    P sendPutTTL(String server, String id, byte[] body, long ttl) throws IOException, InterruptedException, HttpException, PoolException;
    D sendDelete(String server, String id) throws IOException, InterruptedException, HttpException, PoolException;
}
