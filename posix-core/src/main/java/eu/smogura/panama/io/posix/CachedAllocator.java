package eu.smogura.panama.io.posix;

import java.util.concurrent.ConcurrentLinkedDeque;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

public class CachedAllocator /* implements SegmentAllocator */ {
  // TODO For now infinite grow
  // TODO Should we do it allocator for internal purposes or just return raw long address? Last one will be faster

  private final ConcurrentLinkedDeque<MemorySegment> segmentsDequeue[] = new ConcurrentLinkedDeque[Long.SIZE];

  CachedAllocator() {
    for (int i=0; i < segmentsDequeue.length; i++) {
      segmentsDequeue[i] = new ConcurrentLinkedDeque<>();
    }
  }

  public MemorySegment getSegmentByLayout(MemoryLayout layout) {
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
  public MemorySegment getSegment(long size, long alignment) {
    final var alignedSize = (size + alignment - 1) & -alignment;
    final var bitBound = bitBound(alignedSize);

    final var segmentDequeue = segmentsDequeue[bitBound];
    var segment = segmentDequeue.poll();
    if (segment == null) {
      final var allocationSize = 1 << bitBound;
      segment = MemorySegment.allocateNative(allocationSize, ResourceScope.globalScope());
    }

    return segment;
  }

  public void putSegment(MemorySegment memorySegment) {
    final var bitBound= bitBound(memorySegment.byteSize());
    // If size is different, than this segments is not from poll
    assert 1 << bitBound == memorySegment.byteSize();
    segmentsDequeue[bitBound].push(memorySegment);
  }

  private static int bitBound(long alignedSize) {
    var logSize = 64- Long.numberOfLeadingZeros(alignedSize) - 1;
    if (alignedSize <= 1 << logSize)
      return logSize;
    else
      return logSize + 1;
  }
}
