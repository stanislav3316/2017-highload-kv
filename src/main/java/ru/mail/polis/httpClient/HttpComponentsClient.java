package ru.mail.polis.httpClient;

import org.apache.http.HttpResponse;
import org.jetbrains.annotations.NotNull;
import org.apache.http.client.fluent.Request;
import java.io.IOException;

/**
 * Created by iters on 11/17/17.
 */
public class HttpComponentsClient
        implements BasicHttpClient<HttpResponse, HttpResponse, HttpResponse> {
    private static HttpComponentsClient client = null;
    private static final String URL_PATH = "/v0/inner?id=";

    private HttpComponentsClient() {
        //:TODO норм синглтон сделай
        // not allowed to get instances
    }

    public static HttpComponentsClient getInstance() {
        return new HttpComponentsClient();
    }

    public HttpResponse sendGet(@NotNull String server,
                                @NotNull String id) throws IOException {
        return Request.Get(url(id, server)).execute().returnResponse();
    }

    public HttpResponse sendPut(@NotNull String server,
                                @NotNull String id,
                                @NotNull byte[] body) throws IOException {
        return Request.Put(url(id, server)).bodyByteArray(body).execute().returnResponse();
    }

    @Override
    public HttpResponse sendDelete(@NotNull String server,
                                   @NotNull String id) throws IOException {
        return Request.Delete(url(id, server)).execute().returnResponse();
    }

    @NotNull
    private String url(@NotNull final String key,
                              @NotNull final String server) {
        return server + URL_PATH + key;
    }

}
