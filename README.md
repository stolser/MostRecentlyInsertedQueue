**MostRecentlyInsertedQueue** - is an implementation of __*java.util.Queue<E>*__.
The purpose of this queue is to store the N most recently inserted elements.

The queue has the following properties:
* implements the java.util.Queue<E> interface;
* the queue is bounded in size. The total capacity of the queue is passed into the constructor and cannot be changed;
* new elements are added to the tail and removed from the head of the queue;
* the queue is traversed from head to tail;
* the queue is always accepting new elements. If the queue is already full, the oldest element that was inserted is deleted, and the new element is added from the tail;
* this queue does not allow null elements.

**ConcurrentMostRecentlyInsertedQueue** - a thread-safe non-blocking variant of MostRecentlyInsertedQueue.

**MostRecentlyInsertedBlockingQueue** - a thread-safe variant of MostRecentlyInsertedQueue that implements java.util.concurrent.BlockingQueue<E>.
