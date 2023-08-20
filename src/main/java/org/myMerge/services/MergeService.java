package org.myMerge.services;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.myMerge.entity.Node;
import org.myMerge.services.enums.ServiceSetting;

public interface MergeService<T extends Comparable<T>> {
    Node<T> merge(List<Queue<T>> var1, Queue<T> var2, Node<T> var3, PriorityQueue<Node<T>> var4);

    void mergesort(List<Queue<T>> var1, ServiceSetting var2) throws FileNotFoundException;
}
