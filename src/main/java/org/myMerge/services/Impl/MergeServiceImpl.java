package org.myMerge.services.Impl;

import org.myMerge.entity.Node;
import org.myMerge.entity.Setting;
import org.myMerge.services.MergeService;
import org.myMerge.services.MyReaderService;
import org.myMerge.services.MyWriterService;
import org.myMerge.services.enums.ServiceSetting;

import java.io.FileNotFoundException;
import java.util.*;

public class MergeServiceImpl<T extends Comparable<T>> implements MergeService<T> {
    private final MyWriterService writer;
    private final MyReaderService reader;
    private final Setting setting;

    public MergeServiceImpl(MyWriterService writer, MyReaderService reader, Setting setting) {
        this.writer = writer;
        this.reader = reader;
        this.setting = setting;
    }

    public Node<T> merge(List<Queue<T>> data, Queue<T> outData, Node<T> min, PriorityQueue<Node<T>> heap) {
        while(true) {
            if (!heap.isEmpty()) {
                min = heap.poll();
                outData.add(min.getValue());
                int listNumber = min.getListNum();
                if (data.get(listNumber).isEmpty()) {
                    continue;
                }

                min.setValue(data.get(listNumber).poll());
                heap.add(min);
                if (!(data.get(listNumber).isEmpty())) {
                    continue;
                }
            }

            return min;
        }
    }

    private void prepareToNextRun(List<Queue<T>> data, String outFile) {
        data.clear();
        this.reader.prepareToNextRun(outFile);
    }

    public void mergesort(List<Queue<T>> data, ServiceSetting order) throws FileNotFoundException {
        int swapperOutFiles;
        for(swapperOutFiles = 0; !reader.nameFileIsEmpty(); swapperOutFiles = 1 - swapperOutFiles) {
            reader.openAndFillAllFiles(data);
            PriorityQueue<Node<T>> pq = getNodes(data, order);
            mergeNodes(pq, data, swapperOutFiles);
            prepareToNextRun(data, setting.getOutFile().get(swapperOutFiles));
        }

        if (swapperOutFiles != 1) {
            writer.renameTempFileToOutFile(setting, swapperOutFiles);
        }

    }

    private PriorityQueue<Node<T>> getNodes(List<Queue<T>> data, ServiceSetting Order) {
        Comparator compareOperation = null;
        if (Order == ServiceSetting.ORDER_DEC) {
            compareOperation = this.setting.getCompareOperation();
        }

        PriorityQueue<Node<T>> pq = new PriorityQueue<>(compareOperation);

        for(int i = 0; i < data.size(); ++i) {
            if (data.get(i).size() >= 1) {
                pq.add(new Node<>(data.get(i).poll(), i));
            }
        }
        return pq;
    }

    private void mergeNodes(PriorityQueue<Node<T>> pq, List<Queue<T>> data, int swapperOutFiles) {
        Node<T> min = new Node<>();

        while(!pq.isEmpty()) {
            Queue<T> outData = new LinkedList<>();
            min = this.merge(data, outData, min, pq);
            String fileForRecord = setting.getOutFile().get(swapperOutFiles);
            writer.writeAllListElements(outData, fileForRecord, true);
            int numList = min.getListNum();
            reader.readData(data.get(numList), numList);
        }

    }
}
