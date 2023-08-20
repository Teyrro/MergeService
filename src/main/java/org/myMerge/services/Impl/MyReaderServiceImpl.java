package org.myMerge.services.Impl;

import org.myMerge.entity.ReadTemplate;
import org.myMerge.services.MyReaderService;
import org.myMerge.services.enums.ServiceSetting;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MyReaderServiceImpl implements MyReaderService {
    private final int countElements;
    private final int countDirectFilesBound;
    private final List<BufferedReader> br = new ArrayList<>();
    private final Deque<String> nameFiles = new LinkedList<>();
    private final ReadTemplate func;

    public MyReaderServiceImpl(ServiceSetting typeOfData, int countBound,
                               List<String> nameFiles, int countElements) {
        this.countElements = countElements;
        this.nameFiles.addAll(nameFiles);
        this.func = action(typeOfData);
        this.countDirectFilesBound = countBound;
    }

    private ReadTemplate action(ServiceSetting typeOfData) {
        return switch (typeOfData){
            case TYPE_INT -> Integer::valueOf;
            case TYPE_STRING -> (n) -> n;
            default -> throw new IllegalStateException("Unexpected value: " + typeOfData);
        };
    }


    public <T> int readData(Queue<T> data, int indexBufReader) {
        if (br.isEmpty())
            return 0;
        try {
            for (int i = 0; i < this.countElements; ++i) {
                String s;
                if ((s = br.get(indexBufReader).readLine()) == null) {
                    br.get(indexBufReader).close();
                    return 0;
                }

                T a = (T) func.addNewElement(s);
                data.add(a);
            }
        } catch (IOException var6) {
            System.out.println(var6.getMessage());
        } catch (NumberFormatException var7) {
            System.out.println("Find place with \"" + var7.getMessage() + "\" " +
                    "and delete all space between symbols");
        }

        return 0;
    }


    public boolean nameFileIsEmpty() {
        return this.nameFiles.isEmpty();
    }

    public void prepareToNextRun(String fileName) {
        br.clear();
        if (!nameFileIsEmpty()) {
            nameFiles.addFirst(fileName);
        }
    }

    private void OpenFiles() throws FileNotFoundException {
        int i = 0;

        while(!nameFiles.isEmpty() && i++ < countDirectFilesBound) {
            BufferedReader b = new BufferedReader(new FileReader(nameFiles.poll()));
            br.add(b);
        }

    }

    public <T> void openAndFillAllFiles(List<Queue<T>> outData) throws FileNotFoundException {
        OpenFiles();

        for(int i = 0; i < br.size(); ++i) {
            Queue<T> a = new LinkedList<>();
            outData.add(a);
            readData(outData.get(i), i);
        }

    }
}
