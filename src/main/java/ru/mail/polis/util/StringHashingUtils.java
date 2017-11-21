package ru.mail.polis.util;

import org.jetbrains.annotations.NotNull;

/**
 * Created by iters on 11/17/17.
 */
public class StringHashingUtils {
    private static final int BASE = 31;

    // size variable - size of topology collection
    public static long getSimpleHash(int topologySize,
                                     @NotNull String id) {
        long sum = 0;
        long index = 0;

        for (char symbol : id.toCharArray()) {
            sum += ((int) symbol) * Math.pow(BASE, index++);
        }

        return sum % topologySize;
    }

}
