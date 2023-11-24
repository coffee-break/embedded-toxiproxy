package io.github.coffeebreak.embedded.toxiproxy.junit4.utils;

import lombok.SneakyThrows;
import org.h2.tools.Server;
import org.junit.rules.ExternalResource;

public class SqlDatabaseServerRule extends ExternalResource {

  private Server webServer;

  @SneakyThrows
  @Override
  protected void before() {
    webServer = Server.createTcpServer("-tcpPort", "9123", "-tcpAllowOthers", "-ifNotExists");
    webServer.start();
  }

  @Override
  protected void after() {
    webServer.stop();
  }
}
