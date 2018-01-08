package ru.mail.polis.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by iters on 1/3/18.
 */
public class RequestParameters {
    private int ack;
    private int from;
    private static Map<String, RequestParameters> map = new HashMap<>();

    private RequestParameters(String replicas, int topologySize) {
        replicas = (replicas == null || replicas.length() == 0)
                ? (topologySize / 2 + 1) + "/" + topologySize : replicas;

        String[] parts = replicas.split("/");
        ack = Integer.parseInt(parts[0]);
        from = Integer.parseInt(parts[1]);
    }

    public static RequestParameters getRequetInfoInstance(String replicas,
                                                          int topologySize) {
        if (map.containsKey(replicas)) {
            return map.get(replicas);
        }

        RequestParameters req = new RequestParameters(replicas, topologySize);

        if (req.ack == 0 || req.from == 0 || req.ack > req.from) {
            return null;
        }

        map.put(replicas, req);
        return req;
    }

    public int getAck() {
        return ack;
    }

    public int getFrom() {
        return from;
    }
}
