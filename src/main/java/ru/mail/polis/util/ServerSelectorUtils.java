package ru.mail.polis.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by iters on 11/17/17.
 */
public class ServerSelectorUtils {

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

}
