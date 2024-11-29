package com.github.m7w.ajcourse;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<E> {
    private int capacity = 16;
    private int size = 0;
    private Object[] array;

    public CustomArrayList() {
        array = new Object[capacity];
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity should be > 0");
        }
        capacity = initialCapacity;
        array = new Object[capacity];
    }

    public boolean add(E element) {
        if (size == capacity) {
            increaseCapacity();
        }
        array[size] = element;
        size++;
        return true;
    }

    public void add(int index, E element) {
        checkIndex(index);
        if (size == capacity) {
            increaseCapacity();
        }

        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    public boolean addAll(Collection<? extends E> c) {
        if (size + c.size() > capacity) {
            capacity = size + c.size();
            increaseCapacity();
        }
        System.arraycopy(c.toArray(), 0, array, size, c.size());
        size += c.size();
        return true;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }

    public int size() {
        return size;
    }

    public void remove(int index) {
        checkIndex(index);
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        array[--size] = null;
    }

    public void remove(Object o) {
        int pos = indexOf(o);
        if (pos != -1) {
            remove(pos);
        }
    }

    public void sort(Comparator<? super E> c) {
        quicksort(0, size - 1, c);
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public void trimToSize() {
        if (size < capacity) {
            Object[] newArray = new Object[size];
            System.arraycopy(array, 0, newArray, 0, size);
            capacity = size;
            array = newArray;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index " + index + " should be > 0 and <= " + size);
        }
    }

    private void increaseCapacity() {
        capacity *= 2;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void quicksort(int startPos, int endPos, Comparator<? super E> c) {
        if (endPos <= startPos) {
            return;
        }

        int pivotPos = pivot(startPos, endPos, c);
        E pivot = get(pivotPos);
        swap(startPos, pivotPos);

        int i = startPos + 1;
        for (int j = startPos + 1; j <= endPos; j++) {
            if (c.compare(get(j), pivot) < 0) {
                swap(i, j);
                i++;
            }
        }
        swap(startPos, i - 1);

        quicksort(startPos, i - 2, c);
        quicksort(i, endPos, c);
    }

    private void swap(int i, int j) {
        Object tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private int pivot(int startPos, int endPos, Comparator<? super E> c) {
        int mediumPos = (endPos - startPos) / 2;

        if (c.compare(get(startPos), get(mediumPos)) <= 0) {
            if (c.compare(get(mediumPos), get(endPos)) <= 0) {
                return mediumPos;
            } else if (c.compare(get(endPos), get(startPos)) <= 0) {
                return startPos;
            } else {
                return endPos;
            }
        } else {
            if (c.compare(get(startPos), get(endPos)) <= 0) {
                return startPos;
            } else if (c.compare(get(endPos), get(mediumPos)) <= 0) {
                return mediumPos;
            } else {
                return endPos;
            }
        }
    }
}
