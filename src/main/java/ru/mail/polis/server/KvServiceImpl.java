package ru.mail.polis.server;

import one.nio.http.HttpServer;
import one.nio.server.ServerConfig;
import ru.mail.polis.KVService;
import ru.mail.polis.handler.FrontHandler;
import ru.mail.polis.handler.BackHandler;
import ru.mail.polis.storageManager.BasicStorage;
import ru.mail.polis.storageManager.FileBasicStorageImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by iters on 11/16/17.
 */
public class KvServiceImpl
        extends HttpServer implements KVService {

    public KvServiceImpl(ServerConfig config,
                         File dir,
                         Set<String> topology) throws IOException {
        super(config);
        BasicStorage storage = new FileBasicStorageImpl(dir);
        addRequestHandlers(new BackHandler(storage));
        addRequestHandlers(new FrontHandler(topology, this.port, storage));
    }

    @Override
    public void start() {
        super.start();

        // это сделано потому, что сервер не успевал подняться
        // до того как к нему начинают приходить запросы
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
