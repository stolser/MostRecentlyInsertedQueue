package com.railsreactor.util.concurrent;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MostRecentlyInsertedBlockingQueue<E> extends ConcurrentMostRecentlyInsertedQueue<E>
        implements BlockingQueue<E> {

    private final ReentrantLock lock;
    private final Condition notEmpty;

    public MostRecentlyInsertedBlockingQueue(int capacity) {
        super(capacity);
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
    }

    @Override
    public void put(E e) {
        offer(e);
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) {
        return offer(e);
    }

    @Override
    public boolean offer(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();

        boolean isElemWasAdded = super.offer(e);
        try {
            if (isElemWasAdded)
                notEmpty.signalAll();
        } finally {
            lock.unlock();
        }

        return isElemWasAdded;
    }

    @Override
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;

        lock.lockInterruptibly();
        try {
            while (size() == 0)
                notEmpty.await();
            return poll();

        } finally {
            lock.unlock();
        }
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        final ReentrantLock lock = this.lock;

        lock.lockInterruptibly();
        try {
            if (noElementAddedDuringTimeout(nanos)) {
                return null;
            } else {
                return poll();
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean noElementAddedDuringTimeout(long nanos) throws InterruptedException {
        while (size() == 0) {
            if (nanos <= 0)
                return true;
            nanos = notEmpty.awaitNanos(nanos);
        }

        return false;
    }

    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        throw new UnsupportedOperationException();
    }
}
