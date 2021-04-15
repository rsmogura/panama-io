package eu.smogura.panama.io.posix;

public class PosixException extends RuntimeException {
  public PosixException(int errno) {
    super(String.format("Posix error %d", errno));
  }
}
