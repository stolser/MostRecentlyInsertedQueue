package com.railsreactor.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

public class MostRecentlyInsertedQueueTest {
    private Queue<Integer> intQueue;

    @Before
    public void setup() {
        intQueue = new MostRecentlyInsertedQueue<>(5);
        intQueue.offer(1);
        intQueue.offer(2);
        intQueue.offer(3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingQueueWithNegativeCapacityShouldThrowException() {
        new MostRecentlyInsertedQueue<Integer>(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingQueueWithZeroCapacityShouldThrowException() {
        new MostRecentlyInsertedQueue<Integer>(0);
    }

    @Test
    public void capacityShouldBeAlwaysConstant() {
        int capacity = 3;
        MostRecentlyInsertedQueue<Integer> queue = new MostRecentlyInsertedQueue<>(capacity);

        assertEquals(capacity, queue.capacity());

        queue.offer(1);
        assertEquals(capacity, queue.capacity());

        queue.offer(2);
        queue.offer(3);
        queue.add(4);

        assertEquals(capacity, queue.capacity());

        queue.poll();
        queue.remove();
        assertEquals(capacity, queue.capacity());

        queue.peek();
        queue.element();
        assertEquals(capacity, queue.capacity());

        queue.clear();
        assertEquals(capacity, queue.capacity());
    }

    @Test
    public void offerShouldIncreaseSizeByOneWhenQueueIsNotFull() throws Exception {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(3);

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
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(3);

        queue.offer(1);

        Integer[] expected1 = {1};
        Integer[] actual1 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expected1, actual1);
        assertEquals(1, queue.size());

        queue.offer(2);

        Integer[] expected2 = {1, 2};
        Integer[] actual2 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expected2, actual2);
        assertEquals(2, queue.size());

        queue.offer(3);

        Integer[] expected3 = {1, 2, 3};
        Integer[] actual3 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expected3, actual3);
        assertEquals(3, queue.size());

        queue.offer(4);

        Integer[] expected4 = {2, 3, 4};
        Integer[] actual4 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expected4, actual4);
        assertEquals(3, queue.size());

        queue.offer(5);

        Integer[] expected5 = {3, 4, 5};
        Integer[] actual5 = queue.stream().toArray(Integer[]::new);
        assertArrayEquals(expected5, actual5);
        assertEquals(3, queue.size());
    }

    @Test(expected = NullPointerException.class)
    public void offerNullValueShouldThrowException() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(10);
        Integer nullInt = null;

        queue.offer(nullInt);
    }

    @Test
    public void pollEmptyQueueShouldReturnNull() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(5);

        assertNull(queue.poll());
    }

    @Test
    public void pollShouldRemoveOneElementFromHead() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(3);
        queue.offer(1);
        queue.offer(2);
        queue.add(3);

        int poll1 = queue.poll();
        Integer[] expected1 = {2, 3};
        Integer[] actual1 = queue.stream().toArray(Integer[]::new);

        assertEquals(1, poll1);
        assertArrayEquals(expected1, actual1);
        assertEquals(2, queue.size());

        int poll2 = queue.poll();
        Integer[] expected2 = {3};
        Integer[] actual2 = queue.stream().toArray(Integer[]::new);

        assertEquals(2, poll2);
        assertArrayEquals(expected2, actual2);
        assertEquals(1, queue.size());

        int poll3 = queue.poll();
        Integer[] expected3 = {};
        Integer[] actual3 = queue.stream().toArray(Integer[]::new);

        assertEquals(3, poll3);
        assertArrayEquals(expected3, actual3);
        assertEquals(0, queue.size());
    }

    @Test
    public void peekEmptyQueueShouldReturnNull() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(5);

        assertNull(queue.peek());
    }

    @Test
    public void peekShouldReturnHeadElementWithoutRemovingItFromQueue() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(5);

        queue.offer(1);
        queue.offer(2);
        queue.offer(3);

        int peek1 = queue.peek();
        Integer[] expected1 = {1, 2, 3};
        Integer[] actual1 = queue.stream().toArray(Integer[]::new);

        assertEquals(1, peek1);
        assertArrayEquals(expected1, actual1);

        int peek2 = queue.peek();

        assertEquals(peek1, peek2);
    }

    @Test
    public void clearShouldEmptyQueue() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(5);
        queue.offer(1);
        queue.add(2);
        queue.clear();

        assertArrayEquals(new Integer[]{}, queue.stream().toArray(Integer[]::new));
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void queueShouldAcceptDuplicates() {
        Queue<String> queue = new MostRecentlyInsertedQueue<>(5);

        queue.add("one");
        queue.add("two");
        queue.add("one");
        queue.add("one");

        assertEquals(4, queue.size());
    }

    @Test
    public void containsShouldReturnTrueOnlyIfThereIsSuchElementInQueue() {
        Queue<String> queue = new MostRecentlyInsertedQueue<>(5);

        queue.add("one");
        queue.add("two");

        assertTrue(queue.contains("one"));
        assertTrue(queue.contains("two"));
        assertFalse(queue.contains("three"));
    }

    @Test
    public void toArrayWithoutParamShouldReturnNewArrayWithTheSameElements() {
        Object[] expectedArr = {1, 2, 3};
        Object[] actualArr = intQueue.toArray();

        assertArrayEquals(expectedArr, actualArr);

        intQueue.add(4);

        assertArrayEquals(expectedArr, actualArr);

    }

    @Test
    public void toArrayWithParamShouldReturnPassedArrayIfItHasEnoughSize() {
        Integer[] originalArr = new Integer[3];
        Object[] expectedArr = {1, 2, 3};

        Integer[] actualArr = intQueue.toArray(originalArr);

        assertArrayEquals(expectedArr, originalArr);
        assertArrayEquals(expectedArr, actualArr);
        assertTrue(originalArr == actualArr);
    }

    @Test
    public void toArrayWithParamShouldReturnNewArrayIfPassedArrayIsTooSmall() {
        Integer[] originalArr = new Integer[2];
        Object[] expectedArr = {1, 2, 3};

        Integer[] actualArr = intQueue.toArray(originalArr);

        assertArrayEquals(expectedArr, actualArr);
        assertTrue(originalArr != actualArr);
    }

}