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

    @Path("/v0/inner")
    public void handler(Request request,
                         HttpSession session,
                         @Param(value = "id") String id) throws IOException {

        switch (request.getMethod()) {
            case Request.METHOD_GET:
                getController(session, id);
                break;
            case Request.METHOD_PUT:
                putController(request, session, id);
                break;
            case Request.METHOD_DELETE:
                deleteController(session, id);
                break;
            default:
                session.sendError(Response.BAD_REQUEST,
                        "use GET, PUT or DELETE methods");
        }
    }

    private void getController(HttpSession session, String id) throws IOException {
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

    private void putController(Request request, HttpSession session, String id)
            throws IOException {
        byte[] body = request.getBody();
        boolean isPutted = storage.saveData(id, body);

        if (!isPutted) {
            session.sendResponse(new Response(
                    Response.INTERNAL_ERROR, "can't create file".getBytes()));
            return;
        }

        session.sendResponse(new Response(Response.CREATED, "file was created".getBytes()));
    }

    private void deleteController(HttpSession session, String id) throws IOException {
        storage.removeData(id);
        session.sendResponse(new Response(Response.ACCEPTED, "file was deleted".getBytes()));
    }
}
