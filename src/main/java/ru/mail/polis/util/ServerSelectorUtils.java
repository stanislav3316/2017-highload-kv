package ru.mail.polis.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by iters on 11/17/17.
 */
public class ServerSelectorUtils {

    /*
    public static Set<String> getServers(@NotNull Set<String> topology,
                                         final long hash,
                                         final int from) {
        Set<String> servers = new HashSet<>();

        if (topology.size() == 1) {
            return topology;
        }

        List<String> tempTopology = new LinkedList<>(topology);
        long index = hash - 1;

        for (int i = 0; i < from; i++) {
            index = (index + 1) % topology.size();
            servers.add(tempTopology.get((int) index));
        }

        return servers;
    }
    */

    //todo: add cache ?
    public static List<String> getServers(@NotNull List<String> topology,
                                         @NotNull String id,
                                         final int from) {
        if (topology.size() == 1) {
            return topology;
        }

        List<String> servers = new ArrayList<>();
        int index = (Math.abs(id.hashCode()) % topology.size()) - 1;

        for (int i = 0; i < from; i++) {
            index = (index + 1) % topology.size();
            servers.add(topology.get(index));
        }

        return servers;
    }

}
