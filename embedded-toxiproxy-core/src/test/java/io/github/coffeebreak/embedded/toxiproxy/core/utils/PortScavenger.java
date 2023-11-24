package io.github.coffeebreak.embedded.toxiproxy.core.utils;

import java.io.IOException;
import java.net.ServerSocket;

public class PortScavenger {

  public static int getFreePort() {
    try (ServerSocket socket = new ServerSocket(0)) {
      socket.setReuseAddress(true);
      return socket.getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(
          "Could not find a free TCP/IP port to start embedded Jetty HTTP Server on");
    }
  }
}
