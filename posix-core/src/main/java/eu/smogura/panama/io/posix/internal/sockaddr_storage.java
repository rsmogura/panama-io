// Generated by jextract

package eu.smogura.panama.io.posix.internal;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.ValueLayout.*;
public class sockaddr_storage {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_SHORT$LAYOUT.withName("ss_family"),
        MemoryLayout.sequenceLayout(118, Constants$root.C_CHAR$LAYOUT).withName("__ss_padding"),
        Constants$root.C_LONG_LONG$LAYOUT.withName("__ss_align")
    ).withName("sockaddr_storage");
    public static MemoryLayout $LAYOUT() {
        return sockaddr_storage.$struct$LAYOUT;
    }
    static final VarHandle ss_family$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("ss_family"));
    public static VarHandle ss_family$VH() {
        return sockaddr_storage.ss_family$VH;
    }
    public static short ss_family$get(MemorySegment seg) {
        return (short)sockaddr_storage.ss_family$VH.get(seg);
    }
    public static void ss_family$set( MemorySegment seg, short x) {
        sockaddr_storage.ss_family$VH.set(seg, x);
    }
    public static short ss_family$get(MemorySegment seg, long index) {
        return (short)sockaddr_storage.ss_family$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ss_family$set(MemorySegment seg, long index, short x) {
        sockaddr_storage.ss_family$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment __ss_padding$slice(MemorySegment seg) {
        return seg.asSlice(2, 118);
    }
    static final VarHandle __ss_align$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("__ss_align"));
    public static VarHandle __ss_align$VH() {
        return sockaddr_storage.__ss_align$VH;
    }
    public static long __ss_align$get(MemorySegment seg) {
        return (long)sockaddr_storage.__ss_align$VH.get(seg);
    }
    public static void __ss_align$set( MemorySegment seg, long x) {
        sockaddr_storage.__ss_align$VH.set(seg, x);
    }
    public static long __ss_align$get(MemorySegment seg, long index) {
        return (long)sockaddr_storage.__ss_align$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void __ss_align$set(MemorySegment seg, long index, long x) {
        sockaddr_storage.__ss_align$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment allocate(ResourceScope scope) { return allocate(SegmentAllocator.nativeAllocator(scope)); }
    public static MemorySegment allocateArray(int len, ResourceScope scope) {
        return allocateArray(len, SegmentAllocator.nativeAllocator(scope));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, ResourceScope scope) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, scope); }
}


