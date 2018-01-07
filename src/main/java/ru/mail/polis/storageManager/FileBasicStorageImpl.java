package ru.mail.polis.storageManager;

import com.google.common.io.ByteStreams;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * Created by iters on 11/16/17.
 */
public class FileBasicStorageImpl implements BasicStorage {

    private final Set<String> deletedKeys;
    private File workingDir;

    public FileBasicStorageImpl(File workingDir) {
        this.workingDir = workingDir;
        deletedKeys = new HashSet<>();
        //TODO: concurrent operation with data ?
    }

    @Override
    public boolean saveData(String id, byte[] data) {
        deletedKeys.remove(id);

        Path p = Paths.get(workingDir + File.separator + id);

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE, TRUNCATE_EXISTING))) {
            out.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
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
