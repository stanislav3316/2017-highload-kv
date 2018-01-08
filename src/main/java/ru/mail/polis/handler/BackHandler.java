package ru.mail.polis.handler;

import one.nio.http.*;
import one.nio.http.Path;
import ru.mail.polis.storageManager.BasicStorage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by iters on 11/16/17.
 */
public class BackHandler {
    private BasicStorage storage;

    public BackHandler(BasicStorage storage) {
        this.storage = storage;
    }

    @Path("/v0/get")
    public void getController(HttpSession session, @Param(value = "id") String id) throws IOException {
        if (storage.isDeleted(id)) {
            session.sendResponse(new Response(Response.NO_CONTENT, "file was deleted".getBytes()));
            return;
        }

        if (!storage.isDataExist(id)) {
            session.sendResponse(new Response(Response.NOT_FOUND, "file is not found".getBytes()));
            return;
        }

        byte[] result = storage.getData(id);
        session.sendResponse(new Response(Response.OK, result));
    }

    @Path("/v0/put")
    public void putController(Request request, HttpSession session, @Param(value = "id") String id)
            throws IOException {
        byte[] body = request.getBody();
        storage.saveData(id, body);

        session.sendResponse(new Response(
                Response.CREATED, "file was attempted to created".getBytes()));
    }

    @Path("/v0/delete")
    public void deleteController(HttpSession session, @Param(value = "id") String id) throws IOException {
        storage.removeData(id);
        session.sendResponse(new Response(Response.ACCEPTED, "file was deleted".getBytes()));
    }
}
