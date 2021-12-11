// Generated by jextract

package eu.smogura.panama.io.posix.internal;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import jdk.incubator.foreign.*;
import static jdk.incubator.foreign.ValueLayout.*;
public class sockaddr_in6 {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_SHORT$LAYOUT.withName("sin6_family"),
        Constants$root.C_SHORT$LAYOUT.withName("sin6_port"),
        Constants$root.C_INT$LAYOUT.withName("sin6_flowinfo"),
        MemoryLayout.structLayout(
            MemoryLayout.unionLayout(
                MemoryLayout.sequenceLayout(16, Constants$root.C_CHAR$LAYOUT).withName("__u6_addr8"),
                MemoryLayout.sequenceLayout(8, Constants$root.C_SHORT$LAYOUT).withName("__u6_addr16"),
                MemoryLayout.sequenceLayout(4, Constants$root.C_INT$LAYOUT).withName("__u6_addr32")
            ).withName("__in6_u")
        ).withName("sin6_addr"),
        Constants$root.C_INT$LAYOUT.withName("sin6_scope_id")
    ).withName("sockaddr_in6");
    public static MemoryLayout $LAYOUT() {
        return sockaddr_in6.$struct$LAYOUT;
    }
    static final VarHandle sin6_family$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("sin6_family"));
    public static VarHandle sin6_family$VH() {
        return sockaddr_in6.sin6_family$VH;
    }
    public static short sin6_family$get(MemorySegment seg) {
        return (short)sockaddr_in6.sin6_family$VH.get(seg);
    }
    public static void sin6_family$set( MemorySegment seg, short x) {
        sockaddr_in6.sin6_family$VH.set(seg, x);
    }
    public static short sin6_family$get(MemorySegment seg, long index) {
        return (short)sockaddr_in6.sin6_family$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void sin6_family$set(MemorySegment seg, long index, short x) {
        sockaddr_in6.sin6_family$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle sin6_port$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("sin6_port"));
    public static VarHandle sin6_port$VH() {
        return sockaddr_in6.sin6_port$VH;
    }
    public static short sin6_port$get(MemorySegment seg) {
        return (short)sockaddr_in6.sin6_port$VH.get(seg);
    }
    public static void sin6_port$set( MemorySegment seg, short x) {
        sockaddr_in6.sin6_port$VH.set(seg, x);
    }
    public static short sin6_port$get(MemorySegment seg, long index) {
        return (short)sockaddr_in6.sin6_port$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void sin6_port$set(MemorySegment seg, long index, short x) {
        sockaddr_in6.sin6_port$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle sin6_flowinfo$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("sin6_flowinfo"));
    public static VarHandle sin6_flowinfo$VH() {
        return sockaddr_in6.sin6_flowinfo$VH;
    }
    public static int sin6_flowinfo$get(MemorySegment seg) {
        return (int)sockaddr_in6.sin6_flowinfo$VH.get(seg);
    }
    public static void sin6_flowinfo$set( MemorySegment seg, int x) {
        sockaddr_in6.sin6_flowinfo$VH.set(seg, x);
    }
    public static int sin6_flowinfo$get(MemorySegment seg, long index) {
        return (int)sockaddr_in6.sin6_flowinfo$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void sin6_flowinfo$set(MemorySegment seg, long index, int x) {
        sockaddr_in6.sin6_flowinfo$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment sin6_addr$slice(MemorySegment seg) {
        return seg.asSlice(8, 16);
    }
    static final VarHandle sin6_scope_id$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("sin6_scope_id"));
    public static VarHandle sin6_scope_id$VH() {
        return sockaddr_in6.sin6_scope_id$VH;
    }
    public static int sin6_scope_id$get(MemorySegment seg) {
        return (int)sockaddr_in6.sin6_scope_id$VH.get(seg);
    }
    public static void sin6_scope_id$set( MemorySegment seg, int x) {
        sockaddr_in6.sin6_scope_id$VH.set(seg, x);
    }
    public static int sin6_scope_id$get(MemorySegment seg, long index) {
        return (int)sockaddr_in6.sin6_scope_id$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void sin6_scope_id$set(MemorySegment seg, long index, int x) {
        sockaddr_in6.sin6_scope_id$VH.set(seg.asSlice(index*sizeof()), x);
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


