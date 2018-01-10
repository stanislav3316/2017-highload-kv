package ru.mail.polis.storageManager;

import java.io.IOException;

/**
 * Created by iters on 11/16/17.
 */
public interface BasicStorage {
    void saveData(String id, byte[] data) throws IOException;
    boolean removeData(String id);
    byte[] getData(String key) throws IOException;
    boolean isDataExist(String key);
    boolean isDeleted(String key);
    void saveDataWithTTL(String id, byte[] data, long ttl) throws IOException;
}
