package com.railsreactor.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MostRecentlyInsertedQueueIteratorTest {

    private Queue<Integer> queue;
    private Iterator<Integer> iterator;

    @Before
    public void setup() {
        queue = new MostRecentlyInsertedQueue<>(10);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);

        iterator = queue.iterator();
    }

    @Test
    public void iteratorShouldIterateOverAllElementsInCorrectOrder() {
        Iterator<Integer> iterator = queue.iterator();

        Integer[] expected1 = {1, 2, 3, 4, 5};
        Integer[] actual1 = new Integer[5];
        int index = 0;
        while (iterator.hasNext()) {
            actual1[index] = iterator.next();
            index++;
        }

        assertArrayEquals(expected1, actual1);
    }

    @Test(expected = ConcurrentModificationException.class)
    public void nextShouldThrowExceptionIfQueueIsModifiedUsingOffer() {
        queue.offer(10);
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void nextShouldThrowExceptionIfQueueIsModifiedUsingPoll() {
        queue.poll();
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void nextShouldThrowExceptionIfQueueIsModifiedUsingClear() {
        queue.clear();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void nextShouldThrowExceptionIfQueueIsEmpty() {
        queue.clear();
        Iterator<Integer> iterator = queue.iterator();

        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void nextShouldThrowExceptionIfThereIsNoElementsInQueue() {
        while(true) {
            iterator.next();
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void removeShouldThrowExceptionIfQueueIsModified() {
        queue.offer(10);
        iterator.next();
        iterator.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void removeShouldThrowExceptionIfNextHasNotBeenCalled() {
        iterator.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void removeShouldThrowExceptionIfCalledTwice() {
        iterator.next();
        iterator.remove();
        iterator.remove();
    }

    @Test
    public void removeShouldRemoveElementReturnedByNext() {
        iterator.next();
        iterator.remove();

        Integer[] expected1 = {2, 3, 4, 5};
        Integer[] actual1 = queue.stream().toArray(Integer[]::new);
        int next1 = iterator.next();
        iterator.remove();

        assertArrayEquals(expected1, actual1);
        assertEquals(2, next1);

        while(iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }

        Integer[] expected2 = {};
        Integer[] actual2 = queue.stream().toArray(Integer[]::new);

        assertArrayEquals(expected2, actual2);
        assertFalse(iterator.hasNext());
    }

}
