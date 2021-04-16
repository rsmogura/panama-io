package eu.smogura.panama.io.posix;

import eu.smogura.panama.io.posix.internal.posix_io_lnx_h;
import java.io.IOException;
import java.io.InputStream;
import jdk.incubator.foreign.MemorySegment;

public class PosixInputStream extends InputStream {
  private static final PollingAllocator MEM_POLL = PosixIO.POSIX_MEM_POLL;
  private final int fd;

  PosixInputStream(int fd) {
    this.fd = fd;
  }

  @Override
  public int read(byte[] b) throws IOException {
    return read(b, 0, b.length);
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    final var nativeBuff = MEM_POLL.getSegment(len, 1);
    try {
      long readBytes = posix_io_lnx_h.read(fd, nativeBuff.value, len);
      if (readBytes < 0) {
        PosixErrorChecker.throwErrno();
      }
      MemorySegment
          .ofArray(b)
          .asSlice(off)
          .copyFrom(
              nativeBuff.value
                  .asSlice(0, readBytes)
          );
      return (int) readBytes;
    }finally {
      MEM_POLL.putSegment(nativeBuff);
    }
  }

  @Override
  public int read() throws IOException {
    return 0;
  }
}
