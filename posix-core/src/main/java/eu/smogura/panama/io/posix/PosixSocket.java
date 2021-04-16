package eu.smogura.panama.io.posix;

import eu.smogura.panama.io.posix.SpinLockQueue.Entry;
import eu.smogura.panama.io.posix.internal.posix_io_lnx_h;
import eu.smogura.panama.io.posix.internal.sockaddr_in;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import jdk.incubator.foreign.MemorySegment;

public final class PosixSocket extends Socket {
  private int fd;
  private PosixInputStream inputStream;

  public PosixSocket() {
  }

  @Override
  public void connect(SocketAddress endpoint, int timeout) throws IOException {
    if (endpoint == null || !(endpoint instanceof InetSocketAddress)) {
      throw new IllegalArgumentException("Unsupported socket endpoint " + endpoint);
    }

    final var inetEndpoint = (InetSocketAddress) endpoint;
    final var host = inetEndpoint.getAddress();
    final var port = inetEndpoint.getPort();

    if (port < 0 || port >= 65536) {
      throw new IllegalArgumentException("Port value not in TCP/IP range, found " + port);
    }

    short domain = (short) (host instanceof InetAddress ? posix_io_lnx_h.AF_INET() : posix_io_lnx_h.AF_INET6());
    int fd = posix_io_lnx_h.socket(domain, posix_io_lnx_h.SOCK_STREAM(), 0);
    if (fd == -1) {
      PosixErrorChecker.throwErrno();
    }

    // From now on fd is opened nad have to be managed by close
    this.fd = fd;

    // Allocate address structure
    boolean ok = false;
    Entry<MemorySegment> sockAddMemSygmentEntry = null;
    final var addr = host.getAddress();
    try {
      sockAddMemSygmentEntry = PosixIO.POSIX_MEM_POLL.getSegmentByLayout(sockaddr_in.$LAYOUT());
      final var sock_address_ptr = sockAddMemSygmentEntry.value;
      // family is same as domain
      if (host instanceof InetAddress) {
        sockaddr_in.sin_family$set(sock_address_ptr, domain);
        sockaddr_in.sin_port$set(sock_address_ptr, Short.reverseBytes((short) port));
        sockaddr_in.sin_addr$slice(sock_address_ptr).copyFrom(MemorySegment.ofArray(addr));
      }
      int result = posix_io_lnx_h.connect(fd, sock_address_ptr, (int) sockaddr_in.sizeof());
      if (result != 0) {
        throw new IOException("" + PosixBindings.errno());
      }
      ok = true;
    } finally {
      if (sockAddMemSygmentEntry != null) {
        PosixIO.POSIX_MEM_POLL.putSegment(sockAddMemSygmentEntry);
      }
      if (!ok) {
        try {
          close();
        }catch (Exception ignored) {

        }
      }
    }
  }

  @Override
  public InputStream getInputStream() throws IOException {
    if (inputStream == null) {
      inputStream = new PosixInputStream(fd);
    }
    return inputStream;
  }

  @Override
  public synchronized void close() throws IOException {
    if (posix_io_lnx_h.close(fd) < 0)
      PosixErrorChecker.throwErrno();
  }
}
