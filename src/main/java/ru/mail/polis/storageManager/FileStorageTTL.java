package ru.mail.polis.storageManager;

import com.google.common.io.ByteStreams;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

/**
 * Created by iters on 1/10/18.
 */
public class FileStorageTTL implements BasicStorage {

    private final String TTL_LABEL = "user.ttl";

    private final Set<String> deletedKeys;
    private final File workingDir;
    private final File ttlWriteFile;

    public FileStorageTTL(File workingDir) throws IOException {
        this.workingDir = workingDir;
        deletedKeys = new HashSet<>();

        ttlWriteFile = new File(workingDir + File.separator + "ttl.txt");
        ttlWriteFile.delete();
        ttlWriteFile.createNewFile();
    }

    @Override
    public synchronized void saveData(String id, byte[] data) throws IOException {
        deletedKeys.remove(id);
        Path p = Paths.get(workingDir + File.separator + id);

        try (OutputStream out = new BufferedOutputStream(
                Files.newOutputStream(p, CREATE, TRUNCATE_EXISTING))) {
            out.write(data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeData(String id) {
        deletedKeys.add(id);
        return new File(workingDir + File.separator + id).delete();
    }

    /**
     * ATTENTION!
     * Use this method only in tandem with {@link #isDataExist(String)}
     * */
    @Override
    public byte[] getData(String key) throws IOException {
        File file = new File(workingDir + File.separator + key);
        byte[] data = null;

        try (BufferedInputStream fis = new BufferedInputStream(
                new FileInputStream(file))) {
            data = ByteStreams.toByteArray(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public boolean isDataExist(String key) {
        boolean isExist = new File(workingDir + File.separator + key).exists();

        if (!isExist) {
            return false;
        }

        boolean isFresh;
        Path pFile = Paths.get(workingDir + File.separator + key);
        UserDefinedFileAttributeView view = Files
                .getFileAttributeView(pFile, UserDefinedFileAttributeView.class);
        try {
            ByteBuffer buf = ByteBuffer.allocate(view.size(TTL_LABEL));
            view.read(TTL_LABEL, buf);
            buf.flip();
            long ttl = Long.parseLong(Charset.defaultCharset().decode(buf).toString());
            isFresh = ttl >= System.currentTimeMillis();
        } catch (IOException e) {
            e.printStackTrace();
            isFresh = true;
        }

        if (!isFresh) {
            removeData(key);
        }

        return isFresh;
    }

    @Override
    public boolean isDeleted(String key) {
        return deletedKeys.contains(key);
    }

    @Override
    public void saveDataWithTTL(String id, byte[] data, long ttl) throws IOException {
        // да, так плохо делать, но чтобы не дублировать код
        // сделал вызов
        saveData(id, data);

        Path file = Paths.get(workingDir + File.separator + id);
        UserDefinedFileAttributeView view = Files
                .getFileAttributeView(file, UserDefinedFileAttributeView.class);
        view.write(TTL_LABEL,
                Charset.defaultCharset().encode(
                        String.valueOf(ttl)));

        try (FileWriter fw = new FileWriter(ttlWriteFile, true)) {
            fw.append(file.toAbsolutePath().toString() +
                    " " + ttl + System.currentTimeMillis() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
