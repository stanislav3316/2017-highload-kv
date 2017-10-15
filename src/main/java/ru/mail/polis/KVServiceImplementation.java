package ru.mail.polis;


import com.sun.net.httpserver.HttpServer;
import ru.mail.polis.handler.DataHttpHandler;
import ru.mail.polis.handler.GetStatusHttpHandler;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by iters on 9/29/17.
 */
public class KVServiceImplementation implements KVService {
    private HttpServer server;
    private int port;
    private File dataLocation;

    public KVServiceImplementation(int port, File dataLocation) {
        this.port = port;
        this.dataLocation = dataLocation;
    }

    @Override
    public void start() throws IOException {
        InetSocketAddress addr = new InetSocketAddress("localhost", port);
        server = HttpServer.create(addr, -1);
        server.createContext("/v0/status", new GetStatusHttpHandler());
        server.createContext("/v0/entity", new DataHttpHandler(dataLocation));
        server.start();
    }

    @Override
    public void stop() {
        // cannot be re-used
        server.stop(0);
    }
}