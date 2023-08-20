package org.myMerge.entity;

import java.util.Objects;

public class Node<T extends Comparable<T>> implements Comparable {
    private T value;
    private int i;
    public Node(T value, int i) {
        this.value = value;
        this.i = i;
    }

    public Node() {}

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Node<?> node = (Node)o;
            return this.i == node.i && Objects.equals(this.value, node.value);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.value, this.i);
    }



    public int getListNum() {
        return this.i;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public int compareTo(Object o) {
        Node node = (Node)o;
        T secondValue = (T) node.getValue();
        return this.value.compareTo(secondValue);
    }
}
