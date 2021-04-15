package eu.smogura.panama.io.posix;

public class PosixErrorChecker {

  public static void throwErrno() {
    throw new PosixException(PosixBindings.errno());
  }
}
