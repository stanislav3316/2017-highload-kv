package ru.mail.polis.storageManager;

import com.google.common.io.ByteStreams;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * Created by iters on 11/16/17.
 */
public class FileBasicStorageImpl implements BasicStorage {

    private final Set<String> deletedKeys;
    private File workingDir;
    private ExecutorService exec;
    private CacheLRU cache;

    public FileBasicStorageImpl(File workingDir) {
        this.workingDir = workingDir;
        deletedKeys = new HashSet<>();
        exec = Executors.newCachedThreadPool();
        cache = new CacheLRU(50_000);
    }

    @Override
    public void saveData(String id, byte[] data) {
        deletedKeys.remove(id);

        exec.submit(() -> {
            Path p = Paths.get(workingDir + File.separator + id);

            try (OutputStream out = new BufferedOutputStream(
                    Files.newOutputStream(p, CREATE, TRUNCATE_EXISTING))) {
                out.write(data, 0, data.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public boolean removeData(String id) {
        deletedKeys.add(id);
        return new File(workingDir + File.separator + id).delete();
    }

    @Override
    public byte[] getData(String id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        File file = new File(workingDir + File.separator + id);
        byte[] data = null;

        try (FileInputStream fis = new FileInputStream(file)) {
            data = ByteStreams.toByteArray(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        cache.put(id, data);

        return data;
    }

    @Override
    public boolean isDataExist(String key) {
        return new File(workingDir + File.separator + key).exists();
    }

    @Override
    public boolean isDeleted(String key) {
        return deletedKeys.contains(key);
    }


    private static class CacheLRU {
        private static final int INIT_CAPACITY = 500;

        private final Map<String, byte[]> map;
        private final int capacity;

        CacheLRU() {
            capacity = INIT_CAPACITY;
            map = new LinkedHashMap<>(capacity, 1f, true);
        }

        CacheLRU(int capacity) {
            this.capacity = capacity;
            map = new LinkedHashMap<>(capacity, 1f, true);
        }

        void put(String id, byte[] data) {
            if (map.size() >= capacity - 1) {
                map.remove(map.keySet().iterator().next());
            }

            map.put(id, data);
        }

        /**
         * ATTENTION!
         * Use this method only
         * after helper {@link #containsKey(String) if element exist} method
         */
        byte[] get(String id) {
            return map.get(id);
        }

        boolean containsKey(String id) {
            return map.containsKey(id);
        }
    }
}
