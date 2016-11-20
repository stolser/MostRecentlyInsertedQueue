package com.railsreactor.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.Assert.*;

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
    public void iterator_Should_IterateOverAllElements_InCorrectOrder() {
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
    public void next_Should_ThrowException_IfQueueIsModified_UsingOffer() {
        queue.offer(10);
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void next_Should_ThrowException_IfQueueIsModified_UsingPoll() {
        queue.poll();
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void next_Should_ThrowException_IfQueueIsModified_UsingClear() {
        queue.clear();
        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void next_Should_ThrowException_IfQueueIsEmpty() {
        queue.clear();
        Iterator<Integer> iterator = queue.iterator();

        iterator.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void next_Should_ThrowException_IfThereIsNoElementsInQueue() {
        while(true) {
            iterator.next();
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void remove_Should_ThrowException_IfQueueIsModified() {
        queue.offer(10);
        iterator.next();
        iterator.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void remove_Should_ThrowException_IfNextHasNotBeenCalled() {
        iterator.remove();
    }

    @Test(expected = IllegalStateException.class)
    public void remove_Should_ThrowException_IfCalledTwice() {
        iterator.next();
        iterator.remove();
        iterator.remove();
    }

    @Test
    public void remove_Should_RemoveElementReturnedByNext() {
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
