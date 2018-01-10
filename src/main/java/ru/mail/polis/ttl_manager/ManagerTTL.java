package ru.mail.polis.ttl_manager;

import java.io.*;
import java.util.concurrent.Executors;

/**
 * Created by iters on 1/10/18.
 */
public class ManagerTTL {

    private File ttlWriteFile;
    private File tempFile;

    public ManagerTTL(File ttlWriteFile, File tempFile) throws IOException {
        this.ttlWriteFile = ttlWriteFile;
        this.tempFile = tempFile;

        Executors.
                newSingleThreadExecutor().
                submit(new ObsoleteFilesCleaner());
    }

    private class ObsoleteFilesCleaner implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                // очистка файла после предыдущего цикла
                try {
                    tempFile.delete();
                    tempFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try (BufferedReader bf = new BufferedReader(new FileReader(ttlWriteFile));
                     BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

                    String fileWithTtl;
                    while ((fileWithTtl = bf.readLine()) != null) {
                        int delimiterIndex = fileWithTtl.lastIndexOf(" ");
                        long ttl = Long.parseLong(
                                fileWithTtl.substring(delimiterIndex + 1, fileWithTtl.length()));

                        if (ttl <= System.currentTimeMillis()) {
                            new File(fileWithTtl.substring(0, delimiterIndex)).delete();
                        } else {
                            bw.append(fileWithTtl).append("\n");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ttlWriteFile.delete();
                tempFile.renameTo(ttlWriteFile);
            }
        }
    }
}