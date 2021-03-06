package com.railsreactor.util;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
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
    public void creatingQueueWithNegativeCapacity_Should_ThrowException() {
        new MostRecentlyInsertedQueue<Integer>(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creatingQueueWithZeroCapacity_Should_ThrowException() {
        new MostRecentlyInsertedQueue<Integer>(0);
    }

    @Test
    public void capacity_Should_BeAlwaysConstant() {
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
    public void offer_Should_IncreaseSizeByOne_WhenQueueIsNotFull() throws Exception {
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
    public void offerMoreElementsThanCapacity_Should_ContainCorrectElements() {
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
    public void offerNullValue_Should_ThrowException() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(10);
        Integer nullInt = null;

        queue.offer(nullInt);
    }

    @Test
    public void pollEmptyQueue_Should_ReturnNull() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(5);

        assertNull(queue.poll());
    }

    @Test
    public void poll_Should_RemoveOneElement_FromHead() {
        int poll1 = intQueue.poll();
        Integer[] expected1 = {2, 3};
        Integer[] actual1 = intQueue.stream().toArray(Integer[]::new);

        assertEquals(1, poll1);
        assertArrayEquals(expected1, actual1);
        assertEquals(2, intQueue.size());

        int poll2 = intQueue.poll();
        Integer[] expected2 = {3};
        Integer[] actual2 = intQueue.stream().toArray(Integer[]::new);

        assertEquals(2, poll2);
        assertArrayEquals(expected2, actual2);
        assertEquals(1, intQueue.size());

        int poll3 = intQueue.poll();
        Integer[] expected3 = {};
        Integer[] actual3 = intQueue.stream().toArray(Integer[]::new);

        assertEquals(3, poll3);
        assertArrayEquals(expected3, actual3);
        assertEquals(0, intQueue.size());
    }

    @Test
    public void peekEmptyQueue_Should_ReturnNull() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(5);

        assertNull(queue.peek());
    }

    @Test
    public void peek_Should_ReturnHeadElement_WithoutRemovingItFromQueue() {
        int peek1 = intQueue.peek();
        Integer[] expected1 = {1, 2, 3};
        Integer[] actual1 = intQueue.stream().toArray(Integer[]::new);

        assertEquals(1, peek1);
        assertArrayEquals(expected1, actual1);

        int peek2 = intQueue.peek();

        assertEquals(peek1, peek2);
    }

    @Test
    public void clear_Should_EmptyQueue() {
        intQueue.clear();

        assertArrayEquals(new Integer[]{}, intQueue.stream().toArray(Integer[]::new));
        assertEquals(0, intQueue.size());
        assertTrue(intQueue.isEmpty());
    }

    @Test
    public void queue_Should_AcceptDuplicates() {
        Queue<String> queue = new MostRecentlyInsertedQueue<>(5);

        queue.add("one");
        queue.add("two");
        queue.add("one");
        queue.add("one");

        assertEquals(4, queue.size());
    }

    @Test
    public void contains_Should_ReturnTrueOnly_IfThereIsSuchElementInQueue() {
        assertTrue(intQueue.contains(1));
        assertTrue(intQueue.contains(2));
        assertFalse(intQueue.contains(101));
    }

    @Test
    public void toArrayWithoutParam_Should_ReturnNewArrayWithTheSameElements() {
        Object[] expectedArr = {1, 2, 3};
        Object[] actualArr = intQueue.toArray();

        assertArrayEquals(expectedArr, actualArr);

        intQueue.add(4);

        assertArrayEquals(expectedArr, actualArr);
    }

    @Test
    public void toArrayWithParam_Should_ReturnPassedArray_IfItHasEnoughSize() {
        Integer[] originalArr = new Integer[3];
        Object[] expectedArr = {1, 2, 3};

        Integer[] actualArr = intQueue.toArray(originalArr);

        assertArrayEquals(expectedArr, originalArr);
        assertArrayEquals(expectedArr, actualArr);
        assertTrue(originalArr == actualArr);
    }

    @Test
    public void toArrayWithParam_Should_ReturnNewArray_IfPassedArrayIsTooSmall() {
        Integer[] originalArr = new Integer[2];
        Object[] expectedArr = {1, 2, 3};

        Integer[] actualArr = intQueue.toArray(originalArr);

        assertArrayEquals(expectedArr, actualArr);
        assertTrue(originalArr != actualArr);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void queue_Should_BeSerializable() throws IOException, ClassNotFoundException {
        Queue<String> strQueue = new MostRecentlyInsertedQueue<>(5);
        strQueue.add("one");
        strQueue.add("two");
        strQueue.add("three");
        strQueue.add("four");
        strQueue.add("five");

        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream("testdata.dat")))) {

            out.writeObject(intQueue);
            out.writeObject(strQueue);
        }

        Queue<Integer> intQueueFromFile = null;
        Queue<String> strQueueFromFile = null;
        try (ObjectInputStream out = new ObjectInputStream(new BufferedInputStream(
                new FileInputStream("testdata.dat")))) {

            intQueueFromFile = (Queue<Integer>) out.readObject();
            strQueueFromFile = (Queue<String>) out.readObject();
        }

        Integer[] expectedIntArr = {1, 2, 3};
        Integer[] actualIntArr = intQueueFromFile.stream().toArray(Integer[]::new);
        String[] expectedStrArr = {"one", "two", "three", "four", "five"};
        String[] actualStrArr = strQueueFromFile.stream().toArray(String[]::new);

        assertArrayEquals(expectedIntArr, actualIntArr);
        assertArrayEquals(expectedStrArr, actualStrArr);
    }

    @Test
    public void methodsFromAbstractQueue_Should_WorkCorrectly() {
        Queue<Integer> queue = new MostRecentlyInsertedQueue<>(5);
        queue.add(1);
        queue.add(2);

        assertEquals(2, queue.size());

        int element1 = queue.element();
        int element2 = queue.element();

        assertEquals(1, element1);
        assertEquals(1, element2);
        assertEquals(2, queue.size());

        int remove1 = queue.remove();

        assertEquals(1, remove1);
        assertEquals(1, queue.size());

        queue.addAll(Arrays.asList(2, 3, 4, 5));

        Integer[] expectedArr = {2, 2, 3, 4, 5};
        Integer[] actualArr = queue.stream().toArray(Integer[]::new);

        assertArrayEquals(expectedArr, actualArr);

        queue.clear();

        assertEquals(0, queue.size());
    }

}