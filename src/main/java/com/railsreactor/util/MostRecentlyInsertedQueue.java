package com.railsreactor.util;

import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class MostRecentlyInsertedQueue<E> extends AbstractQueue<E> {
    private final int CAPACITY;
    private Object[] queue;

    /**
     * The current number of elements in this queue.
     */
    private int size = 0;

    /**
     * The number of times this queue has been <i>structurally modified</i>.
     * Used by the iterator to throw ConcurrentModificationException.
     */
    private int modificationCount;

    public MostRecentlyInsertedQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be positive.");
        this.CAPACITY = capacity;
        this.queue = new Object[CAPACITY];
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(E element) {
        if (element == null)
            throw new NullPointerException("This queue does NOT support null elements.");

        if (queueIsFull()) {
            moveElementsToTheLeftByOne();
            queue[CAPACITY - 1] = element;
        } else {
            queue[size] = element;
            size++;
        }

        modificationCount++;

        return true;
    }

    private void moveElementsToTheLeftByOne() {
        for(int i = 0; i <= (CAPACITY - 2); i++) {
            queue[i] = queue[i+1];
        }
    }

    private boolean queueIsFull() {
        return (size == CAPACITY);
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    public int capacity() {
        return CAPACITY;
    }

    public Iterator<E> iterator() {
        return new ThisIterator();
    }

    private final class ThisIterator implements Iterator<E> {
        private int expectedModificationCount = modificationCount;
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return (currentIndex < size);
        }

        @Override
        public E next() {
            if (expectedModificationCount != modificationCount)
                throw new ConcurrentModificationException();

            @SuppressWarnings("unchecked")
            E nextElement = (E) queue[currentIndex];
            currentIndex++;

            return nextElement;
        }
    }
}
