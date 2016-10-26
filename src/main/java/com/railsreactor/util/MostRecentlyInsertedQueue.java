package com.railsreactor.util;

import java.io.Serializable;
import java.util.*;

public class MostRecentlyInsertedQueue<E> extends AbstractQueue<E>
        implements Serializable {
    private static final long serialVersionUID = 777;
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
    transient private int modificationCount;

    public MostRecentlyInsertedQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive.");

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
            shiftElementsToTheLeftByOneStartingFromIndex(0);
            queue[CAPACITY - 1] = element;

        } else {
            queue[size] = element;
            size++;
        }

        modificationCount++;

        return true;
    }

    private void shiftElementsToTheLeftByOneStartingFromIndex(int startIndex) {
        for (int i = startIndex; i <= (size - 2); i++) {
            queue[i] = queue[i + 1];
        }
    }

    private boolean queueIsFull() {
        return (size == CAPACITY);
    }

    @Override
    public E poll() {
        if (size == 0)
            return null;

        @SuppressWarnings("unchecked")
        E result = (E) queue[0];
        shiftElementsToTheLeftByOneStartingFromIndex(0);
        queue[size - 1] = null;
        size--;
        modificationCount++;

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        return (size == 0) ? null : (E) queue[0];
    }

    @Override
    public void clear() {
        modificationCount++;

        for (int i = 0; i < size; i++)
            queue[i] = null;

        size = 0;
    }

    public int capacity() {
        return CAPACITY;
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    private int indexOf(Object obj) {
        if (obj != null) {
            for (int i = 0; i < size; i++)
                if (obj.equals(queue[i]))
                    return i;
        }

        return -1;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(queue, size);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] newArray) {
        final int size = this.size;
        if (newArray.length < size)
            return (T[]) Arrays.copyOf(queue, size, newArray.getClass());

        System.arraycopy(queue, 0, newArray, 0, size);

        if (newArray.length > size)
            newArray[size] = null;

        return newArray;
    }

    @Override
    public Iterator<E> iterator() {
        return new ThisIterator();
    }

    private final class ThisIterator implements Iterator<E> {
        private int expectedModificationCount = modificationCount;
        private int currentIndex = 0;
        private int lastReturnedElementIndex = -1;

        @Override
        public boolean hasNext() {
            return (currentIndex < size);
        }

        @Override
        public E next() {
            checkQueueNotIllegallyModified();

            if (hasNext()) {
                return getNextElement();
            } else {
                throw new NoSuchElementException("The queue has reached its end. " +
                        "Next time try to call hasNext() first.");
            }
        }

        @Override
        public void remove() {
            checkQueueNotIllegallyModified();

            if (lastReturnedElementIndex >= 0) {
                removeLastReturnedElement();
            } else {
                throw new IllegalStateException("next() hasn't yet been called, or " +
                        "remove() has been called twice");
            }

        }

        private void checkQueueNotIllegallyModified() {
            if (queueIsModifiedFromOutside())
                throw new ConcurrentModificationException("The queue was modified from outside this iterator.");
        }

        private boolean queueIsModifiedFromOutside() {
            return expectedModificationCount != modificationCount;
        }

        private E getNextElement() {
            @SuppressWarnings("unchecked")
            E nextElement = (E) queue[currentIndex];
            lastReturnedElementIndex = currentIndex;
            currentIndex++;

            return nextElement;
        }

        private void removeLastReturnedElement() {
            shiftElementsToTheLeftByOneStartingFromIndex(lastReturnedElementIndex);
            queue[size - 1] = null;
            size--;
            lastReturnedElementIndex = -1;
            currentIndex--;
        }
    }

}
