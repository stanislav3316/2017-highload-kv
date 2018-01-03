package ru.mail.polis.storageManager;

import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by iters on 11/16/17.
 */
public class FileBasicStorageImpl implements BasicStorage {

    private final Set<String> deletedKeys;
    private File workingDir;

    public FileBasicStorageImpl(File workingDir) {
        this.workingDir = workingDir;
        deletedKeys = new HashSet<>();
    }

    @Override
    public boolean saveData(String id, byte[] data) {
        deletedKeys.remove(id);

        File file = new File(workingDir + File.separator + id);

        if (file.exists()) {
            file.delete();
        }

        boolean isCreated;
        try {
            isCreated = file.createNewFile();
        } catch (IOException e) {
            isCreated = false;
        }

        boolean isWritten = true;
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        } catch (IOException e) {
            isWritten = false;
        }

        return isCreated && isWritten;
    }

    @Override
    public boolean removeData(String id) {
        deletedKeys.add(id);
        File file = new File(workingDir + File.separator + id);
        return file.delete();
    }

    @Override
    public byte[] getData(String id) {
        File file = new File(workingDir + File.separator + id);
        byte[] data = null;

        if (!file.exists()) {
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            data = ByteStreams.toByteArray(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public boolean isDataExist(String key) {
        File file = new File(workingDir + File.separator + key);
        return file.exists();
    }

    @Override
    public boolean isDeleted(String key) {
        return deletedKeys.contains(key);
    }
}
