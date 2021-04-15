package eu.smogura.panama.io.posix;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

public class PosixSocketFactory extends SocketFactory {

  @Override
  public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
    return createSocket(InetAddress.getByName(host), port);
  }

  @Override
  public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
      throws IOException, UnknownHostException {
    return null;
  }

  @Override
  public Socket createSocket(InetAddress host, int port) throws IOException {
    final var socket = new PosixSocket();
    socket.connect(new InetSocketAddress(host, port));
    return socket;
  }

  @Override
  public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
      throws IOException {
    return null;
  }
}
