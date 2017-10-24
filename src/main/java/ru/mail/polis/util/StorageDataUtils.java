package ru.mail.polis.util;

import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by iters on 10/22/17.
 */
public class StorageDataUtils {

    public static byte[] getDataFromStorage(String key, File path) {
        File storage = new File(path + File.separator + key);
        if (!storage.exists()) {
            return null;
        }

        try {
            return IOUtils.readFully(new FileInputStream(storage), -1, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void createOrUpdateFiles(String key, byte[] data, File path) throws IOException {
        File storage = new File(path + File.separator + key);

        if(storage.exists()) {
            ru.mail.polis.Files.deleteFileByName(path.toString(), key);
        }

        try {
            ru.mail.polis.Files.createFileAndWriteValue(path.toString(), key, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
