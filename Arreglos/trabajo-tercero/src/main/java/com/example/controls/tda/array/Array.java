package com.example.controls.tda.array;
import com.example.controls.exception.ArrayEmpty;
import com.example.controls.exception.ArrayPositionException;

public class Array {
    private int size;
    private Object[] array;

    public Array(int size) {
        this.size = size;
        this.array = new Object[size];
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Object[] getArray() {
        return array;
    }

    public void setArray(Object[] array) {
        this.array = array;
    }

    public boolean isEmpty() {
        return array == null || size == 0;
    }

    public Object getFirst() {
        if (!isEmpty()) {
            return array[0];
        } else {
            return "Array is Empty";
        }
    }

    public Object getLast() {
        if (!isEmpty()) {
            return array[size - 1];
        } else {
            return "Array is Empty";
        }
    }

    public Object getData(int pos) throws ArrayEmpty, ArrayPositionException {
        if (isEmpty()) {
            throw new ArrayEmpty("Array is Empty");
        } else if (pos < 0 || pos >= size) {
            throw new ArrayPositionException("Index out of range");
        } else {
            return array[pos];
        }
    }

    public void setData(int pos, Object value) throws ArrayEmpty, ArrayPositionException {
        if (isEmpty()) {
            throw new ArrayEmpty("Array is Empty");
        } else if (pos < 0 || pos >= size) {
            throw new ArrayPositionException("Index out of range");
        } else {
            array[pos] = value;
        }
    }

    public void addFirst(Object value) {
        if (isEmpty()) {
            array[0] = value;
        } else {
            for (int i = size - 1; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = value;
        }
    }

    public void addLast(Object value) {
        if (isEmpty()) {
            array[0] = value;
        } else {
            array[size - 1] = value;
        }
    }

    public void merge(Object value, int pos) throws ArrayPositionException {
        if (pos < 0 || pos >= size) {
            throw new ArrayPositionException("Index out of range");
        } else {
            array[pos] = value;
        }
    }

    public void deleteData(int pos) throws ArrayPositionException {
        if (pos < 0 || pos >= size) {
            throw new ArrayPositionException("Index out of range");
        } else {
            for (int i = pos; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            array[size - 1] = null;
        }
    }

    public void addData(Object value) {
        for (int i = 0; i < size; i++) {
            if (array[i] == null) {
                array[i] = value;
                break;
            }
        }
    }

    public Object[] getSerializable() {
        Object[] aux = new Object[size];
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (array[i] != null) {
                aux[index++] = array[i];
            }
        }
        return aux;
    }

    public void print() {
        System.out.println("Array:");
        for (int i = 0; i < size; i++) {
            if (array[i] != null) {
                System.out.println(array[i]);
            }
        }
    }

    public void clear() {
        array = new Object[size];
    }

    public int getCount() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (array[i] != null) {
                count++;
            }
        }
        return count;
    }

    public void delete(int pos) throws ArrayPositionException {
        if (pos < 0 || pos >= size) {
            throw new ArrayPositionException("Index out of range");
        } else {
            array[pos] = null;
            for (int i = pos; i < size - 1; i++) {
                array[i] = array[i + 1];
            }
            array[size - 1] = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (array[i] != null) {
                out.append(array[i]).append(" -> ");
            }
        }
        return out.toString();
    }
}