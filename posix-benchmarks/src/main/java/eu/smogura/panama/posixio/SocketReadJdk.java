package eu.smogura.panama.posixio;

import java.net.Socket;
import javax.net.SocketFactory;

public class SocketReadJdk extends SocketReadCommon {

  @Override
  protected Socket createReadSocket(short port) throws Exception {
    return SocketFactory.getDefault().createSocket("127.0.0.1", port);
  }
}
