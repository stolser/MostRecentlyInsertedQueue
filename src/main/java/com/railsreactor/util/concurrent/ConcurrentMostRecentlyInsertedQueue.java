package com.railsreactor.util.concurrent;

import com.railsreactor.util.MostRecentlyInsertedQueue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ConcurrentMostRecentlyInsertedQueue<E> extends AbstractQueue<E> {
    private MostRecentlyInsertedQueue<E> queue;
    private final Object mutex;

    public ConcurrentMostRecentlyInsertedQueue(int capacity) {
        this.queue = new MostRecentlyInsertedQueue<>(capacity);
        this.mutex = this;
    }

    @Override
    public boolean add(E e) {
        synchronized (mutex) {
            return queue.add(e);
        }
    }

    @Override
    public E remove() {
        synchronized (mutex) {
            return queue.remove();
        }
    }

    @Override
    public E element() {
        synchronized (mutex) {
            return queue.element();
        }
    }

    @Override
    public void clear() {
        synchronized (mutex) {
            queue.clear();
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        synchronized (mutex) {
            return queue.addAll(c);
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (mutex) {
            return queue.isEmpty();
        }
    }

    @Override
    public boolean contains(Object obj) {
        synchronized (mutex) {
            return queue.contains(obj);
        }
    }

    @Override
    public Object[] toArray() {
        synchronized (mutex) {
            return queue.toArray();
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        synchronized (mutex) {
            return queue.toArray(a);
        }
    }

    @Override
    public boolean remove(Object obj) {
        synchronized (mutex) {
            return queue.remove(obj);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        synchronized (mutex) {
            return queue.containsAll(c);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronized (mutex) {
            return queue.removeAll(c);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronized (mutex) {
            return queue.retainAll(c);
        }
    }

    @Override
    public String toString() {
        synchronized (mutex) {
            return queue.toString();
        }
    }

    @Override
    public int hashCode() {
        synchronized (mutex) {
            return queue.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        synchronized (mutex) {
            return queue.equals(obj);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return queue.iterator(); // must be synchronized by user
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        synchronized (mutex) {
            queue.forEach(action);
        }
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        synchronized (mutex) {
            return queue.removeIf(filter);
        }
    }

    @Override
    public Spliterator<E> spliterator() {
        return queue.spliterator(); // must be synchronized by user
    }

    @Override
    public Stream<E> stream() {
        return queue.stream(); // must be synchronized by user
    }

    @Override
    public Stream<E> parallelStream() {
        return queue.parallelStream(); // must be synchronized by user
    }

    @Override
    public int size() {
        synchronized (mutex) {
            return queue.size();
        }
    }

    @Override
    public boolean offer(E e) {
        synchronized (mutex) {
            return queue.offer(e);
        }
    }

    @Override
    public E poll() {
        synchronized (mutex) {
            return queue.poll();
        }
    }

    @Override
    public E peek() {
        synchronized (mutex) {
            return queue.peek();
        }
    }
}
