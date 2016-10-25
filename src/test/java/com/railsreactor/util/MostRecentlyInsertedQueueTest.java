package com.railsreactor.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Queue;

import static org.junit.Assert.*;

public class MostRecentlyInsertedQueueTest {

    @Test
    public void capacityShouldBeAlwaysConstant() {
        int capacity = 3;
        MostRecentlyInsertedQueue<Integer> queue = new MostRecentlyInsertedQueue<>(capacity);

        assertEquals(capacity, queue.capacity());

        queue.offer(1);
        assertEquals(capacity, queue.capacity());

        queue.offer(2);
        queue.offer(3);
        queue.offer(4);

        assertEquals(capacity, queue.capacity());
    }

    @Test
    public void offerShouldIncreaseSizeByOneWhenQueueIsNotFull() throws Exception {
        MostRecentlyInsertedQueue<Integer> queue = new MostRecentlyInsertedQueue<>(3);

        assertEquals(0, queue.size());

        queue.offer(1);
        assertEquals(1, queue.size());

        queue.offer(2);
        assertEquals(2, queue.size());

        queue.offer(3);
        assertEquals(3, queue.size());
    }

    @Test
    public void offerMoreElementsThanCapacityShouldContainCorrectElements() {
        MostRecentlyInsertedQueue<Integer> queue = new MostRecentlyInsertedQueue<>(3);

        queue.offer(1);

        Integer[] expacted1 = {1};
        Integer[] actual1 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expacted1, actual1);

        queue.offer(2);

        Integer[] expacted2 = {1, 2};
        Integer[] actual2 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expacted2, actual2);

        queue.offer(3);

        Integer[] expacted3 = {1, 2, 3};
        Integer[] actual3 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expacted3, actual3);

        queue.offer(4);

        Integer[] expacted4 = {2, 3, 4};
        Integer[] actual4 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expacted4, actual4);

        queue.offer(5);

        Integer[] expacted5 = {3, 4, 5};
        Integer[] actual5 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expacted5, actual5);
    }

    @Test(expected = NullPointerException.class)
    public void offerNullValueShouldThrowException() {
        MostRecentlyInsertedQueue<Integer> queue = new MostRecentlyInsertedQueue<>(10);
        Integer nullInt = null;

        queue.offer(nullInt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingQueueWithNegativeCapacityShouldThrowException() {
        new MostRecentlyInsertedQueue<Integer>(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingQueueWithZeroCapacityShouldThrowException() {
        new MostRecentlyInsertedQueue<Integer>(0);
    }

//    @Test
//    public void iterator() throws Exception {
//
//    }
//
//    @Test
//    public void offer() throws Exception {
//
//    }
//
//    @Test
//    public void poll() throws Exception {
//
//    }
//
//    @Test
//    public void peek() throws Exception {
//
//    }
}