package eu.smogura.panama.posixio;

import eu.smogura.panama.io.posix.PosixSocketFactory;
import java.net.Socket;
import org.openjdk.jmh.annotations.Fork;

public class SocketReadPosix extends SocketReadCommon {

  @Override
  protected Socket createReadSocket(short port) throws Exception {
    return new PosixSocketFactory().createSocket("127.0.0.1", port);
  }
}
