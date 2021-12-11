package eu.smogura.panama.io.posix;

import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;

public class PosixBindings {
  private static final MemorySegment errnoSegment;

  private static VarHandle errno_vh =  ValueLayout.JAVA_INT.varHandle().withInvokeExactBehavior();
  static {
//    final var libraryLookup = CLinker.systemCLinker().lookup();
    final var cLinker = CLinker.systemCLinker();

    // XXX This is incorrect! Errno is defined in other way, deep-dive into errno for given platform
    final var errnoAddress = cLinker.lookup("errno").get().address();
    errnoSegment = MemorySegment.ofAddress(errnoAddress, ValueLayout.JAVA_INT.byteSize(), ResourceScope.globalScope());
  }

  public static int errno() {
    return (int) errno_vh.get(errnoSegment);
  }
}
