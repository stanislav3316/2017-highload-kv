package ru.mail.polis.message;

/**
 * Created by iters on 1/3/18.
 */
public class RequestParameters {
    private final String replicas;
    private int ack;
    private int from;

    private RequestParameters(String replicas, int topologySize) {
        this.replicas = (replicas == null || replicas.length() == 0)
                ? (topologySize / 2 + 1) + "/" + topologySize : replicas;
        computeParameters();
    }

    public static RequestParameters getRequetInfoInstance(String replicas,
                                                          int topologySize) {
        //todo: caching
        RequestParameters req = new RequestParameters(replicas, topologySize);

        if (req.ack == 0 || req.from == 0 || req.ack > req.from) {
            return null;
        }

        return req;
    }

    private void computeParameters() {
        ack = Integer.parseInt(replicas.split("/")[0]);
        from = Integer.parseInt(replicas.split("/")[1]);
    }

    public int getAck() {
        return ack;
    }

    public int getFrom() {
        return from;
    }
}
