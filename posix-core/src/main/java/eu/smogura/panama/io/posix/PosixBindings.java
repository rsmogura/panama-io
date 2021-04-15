package eu.smogura.panama.io.posix;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.LibraryLookup;
import jdk.incubator.foreign.MemoryAccess;
import jdk.incubator.foreign.MemorySegment;

public class PosixBindings {
  private static final MemorySegment ALL_MEM = MemorySegment.globalNativeSegment();

  private static final long errnoAddress;

  static {
    final var libraryLookup = LibraryLookup.ofDefault();
    final var cLinker = CLinker.getInstance();

    errnoAddress = libraryLookup.lookup("errno").get().address().toRawLongValue();

  }

  public static int errno() {
    return MemoryAccess.getIntAtOffset(ALL_MEM, errnoAddress);
  }
}
