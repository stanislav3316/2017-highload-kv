package ru.mail.polis.util;

import org.apache.http.HttpResponse;
import org.jetbrains.annotations.NotNull;
import org.apache.http.client.fluent.Request;
import java.io.IOException;

/**
 * Created by iters on 10/23/17.
 */
public class HttpClientUtils {
    public static HttpResponse get(@NotNull final String key,
                             @NotNull final String server) throws IOException {
        return Request.Get(url(key, server)).execute().returnResponse();
    }

    public static HttpResponse delete(@NotNull final String key,
                                      @NotNull final String server) throws IOException {
        return Request.Delete(url(key, server)).execute().returnResponse();
    }

    public static HttpResponse put(
            @NotNull final String key,
            @NotNull final String server,
            @NotNull final byte[] data) throws IOException {
        return Request.Put(url(key, server)).bodyByteArray(data).execute().returnResponse();
    }

    @NotNull
    private static String url(@NotNull final String key, @NotNull final String server) {
        return server + "/v0/entity?id=" + key + "&locally";
    }

}
