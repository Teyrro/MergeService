package org.myMerge.services;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Queue;

public interface MyReaderService {

    <T> int readData(Queue<T> data, int indexBufReader);

    boolean nameFileIsEmpty();

    void prepareToNextRun(String fileName);

    <T> void openAndFillAllFiles(List<Queue<T>> outData) throws FileNotFoundException;
}