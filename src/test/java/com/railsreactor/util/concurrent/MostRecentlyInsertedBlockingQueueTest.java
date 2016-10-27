package com.railsreactor.util.concurrent;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MostRecentlyInsertedBlockingQueueTest {
    private BlockingQueue<Integer> queue;

    @Before
    public void setup() {
        queue = new MostRecentlyInsertedBlockingQueue<>(5);

    }

    @Test(expected = IllegalArgumentException.class)
    public void instantiatingWithNegativeCapacityShouldThrowException() {
        new MostRecentlyInsertedBlockingQueue(-5);
    }

    @Test
    public void takeFromEmptyQueueShouldNotReturnNullButWaiteForElements()
            throws InterruptedException {

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.offer(7);
            queue.offer(10);
        }).start();

        int take1 = queue.take();
        int take2 = queue.take();

        assertEquals(7, take1);
        assertEquals(10, take2);
    }

    @Test
    public void pollWithTimeoutFromEmptyQueueShouldNotReturnNullButWaiteForElements()
            throws InterruptedException {

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.offer(22);
        }).start();

        int poll1 = queue.poll(2500, TimeUnit.SECONDS);

        assertEquals(22, poll1);
    }

    @Test(timeout = 2100)
    public void pollWithTimeoutShouldReturnNullIfTimeElapses() throws InterruptedException {

        Integer poll = queue.poll(2, TimeUnit.SECONDS);

        assertNull(poll);
    }

    @Test(timeout = 100)
    public void putAndOfferWithTimeoutShouldNotBlockIfSizeEqualCapacity() throws InterruptedException {
        queue.offer(1, 100, TimeUnit.SECONDS);
        queue.offer(2, 100, TimeUnit.SECONDS);
        queue.offer(3, 100, TimeUnit.SECONDS);
        queue.put(4);
        queue.put(5);
        queue.offer(6, 100, TimeUnit.SECONDS);
        queue.put(7);
    }

    @Test
    public void remainingCapacityShouldAlwaysReturnMaxValue() throws InterruptedException {
        assertEquals(Integer.MAX_VALUE, queue.remainingCapacity());

        queue.offer(1);
        queue.add(2);
        queue.put(3);

        assertEquals(Integer.MAX_VALUE, queue.remainingCapacity());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void drainToShouldThrowException() {
        queue.drainTo(new ArrayList<>());
    }
}