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
    // private CacheLRU cache;
    private final Map<String, byte[]> cache;

    public FileBasicStorageImpl(File workingDir) {
        this.workingDir = workingDir;
        deletedKeys = new HashSet<>();
        exec = Executors.newCachedThreadPool();
        // cache = new CacheLRU();
        cache = new HashMap<>(1000);
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
        File file = new File(workingDir + File.separator + id);
        return file.delete();
    }

    @Override
    public byte[] getData(String id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

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

        cache.put(id, data);

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


    private static class CacheLRU {
        private static final int INIT_CAPACITY = 3000;
        private static final int MAX_SIZE = 1024 * 8;

        private final Map<String, byte[]> map;
        private final int capacity;

        public CacheLRU() {
            capacity = INIT_CAPACITY;
            map = new LinkedHashMap<>(capacity, 1f, true);
        }

        public CacheLRU(int capacity) {
            this.capacity = capacity;
            map = new LinkedHashMap<>(capacity, 1f, true);
        }

        public synchronized void put(String id, byte[] data) {
            if (data.length <= MAX_SIZE) {
                return;
            }

            while (map.size() >= capacity - 1) {
                map.remove(map.keySet().iterator().next());
            }

            map.put(id, data);
        }

        public byte[] get(String id) {
            if (map.containsKey(id)) {
                return map.get(id);
            }

            return null;
        }

        public boolean constainsKey(String id) {
            return map.containsKey(id);
        }

    }
}
