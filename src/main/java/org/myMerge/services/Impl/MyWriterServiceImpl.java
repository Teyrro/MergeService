package org.myMerge.services.Impl;

import org.myMerge.entity.Setting;
import org.myMerge.entity.WriteTemplate;
import org.myMerge.services.MyWriterService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class MyWriterServiceImpl implements MyWriterService {
    private BufferedWriter bw;
    private final WriteTemplate func = String::valueOf;

    public MyWriterServiceImpl(String outFileName) {
        Queue<String> q = new LinkedList<>();
        writeAllListElements(q, outFileName, false);
    }

    public <T> void writeAllListElements(Queue<T> outData, String outFileName, boolean isAppend) {
        try {
            bw = new BufferedWriter(new FileWriter(outFileName, isAppend));
            StringBuilder s = new StringBuilder();

            while(!outData.isEmpty()) {
                s.append(func.addNewElement(outData.poll())).append("\n");
            }

            this.bw.write(s.toString());
            this.bw.close();
        } catch (IOException var5) {
            throw new RuntimeException(var5);
        }
    }

    public void renameTempFileToOutFile(Setting setting, int fileIndex) {
        File file1 = new File(setting.getOutFile().get(fileIndex + 1));
        File file2 = new File(setting.getOutFile().get(fileIndex));
        file2.delete();
        boolean success = file1.renameTo(file2);
        if (success) {
            System.out.println("File Rename successfuly");
        } else {
            System.out.println("File is not Rename");
        }

    }
}
