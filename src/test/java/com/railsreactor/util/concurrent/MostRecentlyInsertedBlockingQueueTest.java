package com.railsreactor.util.concurrent;

import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class MostRecentlyInsertedBlockingQueueTest {

    @Test
    public void takeFromEmptyQueueShouldNotReturnNullButWaiteForElements() throws InterruptedException {
        BlockingQueue<Integer> queue = new MostRecentlyInsertedBlockingQueue<>(5);

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

    @Test(timeout = 2100)
    public void pollWithTimeoutShouldReturnNullIfTimeElapses() throws InterruptedException {
        BlockingQueue<Integer> queue = new MostRecentlyInsertedBlockingQueue<>(5);

        Integer poll = queue.poll(2, TimeUnit.SECONDS);

        assertNull(poll);
    }
}