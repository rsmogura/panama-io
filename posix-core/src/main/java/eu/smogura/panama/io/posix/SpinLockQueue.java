package eu.smogura.panama.io.posix;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import jdk.incubator.foreign.MemorySegment;

/**
 * Fast, concurrent LIFO queue (stack).
 * This queue is designed for fast push / pop operations. Synchronization is
 * provided by spin lock.
 *
 * @param <T>
 */
public final class SpinLockQueue<T> {

  private final AtomicInteger lock = new AtomicInteger();
  private volatile Entry<T> head;

  public Entry<T> pollEntry() {
    while (!lock.compareAndSet(0, 1)) { }
    try {
      final var current = head;
      if (current != null) {
        head = current.next;
      }
      return current;
    } finally {
      lock.set(0);
    }
  }

  public void putEntry(Entry<T> entry) {
    while (!lock.compareAndSet(0, 1)) { }
    try {
      entry.next = head;
      head = entry;
    } finally {
      lock.set(0);
    }
  }

  public static class Entry<T> {
    private Entry<T> next;
    public final MemorySegment value;

    public Entry(MemorySegment value) {
      this.value = value;
    }
  }
}
