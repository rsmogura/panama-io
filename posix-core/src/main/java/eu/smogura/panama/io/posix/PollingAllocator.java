package eu.smogura.panama.io.posix;

import eu.smogura.panama.io.posix.SpinLockQueue.Entry;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public class PollingAllocator /* implements SegmentAllocator */ {
  // TODO For now infinite grow
  // TODO Should we do it allocator for internal purposes or just return raw long address? Last one will be faster

  private final SpinLockQueue segmentsDequeue[] = new SpinLockQueue[Long.SIZE];

  PollingAllocator() {
    for (int i=0; i < segmentsDequeue.length; i++) {
      segmentsDequeue[i] = new SpinLockQueue();
    }
  }

  public Entry<MemorySegment> getSegmentByLayout(MemoryLayout layout) {
    return getSegment(layout.byteSize(), layout.byteAlignment());
  }
  /**
   * Gets segment from poll or allocates new one. Internally segments are cached.
   * The size of segment can be larger than requested.
   *
   * @param size the size of segment.
   *
   * @return segment of size at least `size`
   */
  public Entry<MemorySegment> getSegment(long size, long alignment) {
    final var alignedSize = (size + alignment - 1) & -alignment;
    final var bitBound = bitBound(alignedSize);

    final var segmentDequeue = segmentsDequeue[bitBound];
    var segment = segmentDequeue.pollEntry();
    if (segment == null) {
      segment = allocateNewEntry(bitBound);
    }

    return segment;
  }

  public void putSegment(Entry<MemorySegment> entry) {
    final var bitBound= bitBound(entry.value.byteSize());
    // If size is different, than this segments is not from poll
//    assert 1 << bitBound == memorySegment.byteSize();
    segmentsDequeue[bitBound].putEntry(entry);
  }

  private static int bitBound(long alignedSize) {
    var logSize = 64- Long.numberOfLeadingZeros(alignedSize) - 1;
    if (alignedSize <= 1 << logSize)
      return logSize;
    else
      return logSize + 1;
  }

  private Entry<MemorySegment> allocateNewEntry(int bitBound) {
    final var allocationSize = 1 << bitBound;
    return new Entry(MemorySegment.allocateNative(allocationSize, ResourceScope.globalScope()));
  }
}
