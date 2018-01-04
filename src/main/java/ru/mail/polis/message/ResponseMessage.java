package ru.mail.polis.message;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

/**
 * Created by iters on 1/4/18.
 */
public class ResponseMessage {
    public final int code;
    private static final byte[] EMPTY_DATA_ARRAY = new byte[0];
    private byte[] data;

    public ResponseMessage(int code) {
        this.code = code;
        data = EMPTY_DATA_ARRAY;
    }

    public ResponseMessage(int code, @NotNull byte[] data) {
        this.code = code;
        this.data = data;
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

}
