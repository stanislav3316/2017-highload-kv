package ru.mail.polis.util;

/**
 * Created by iters on 10/23/17.
 */
public class StringHashingUtils {
    private static final int base = 31;

    // size variable - size of topology collection
    public static long getSimpleHash(int size, String key) {
        long sum = 0;
        long index = 0;

        for (char symbol : key.toCharArray()) {
            sum += ((int) symbol) * Math.pow(base, index++);
        }

        return sum % size;
    }

}
