package io.github.coffeebreak.embedded.toxiproxy.junit4;

import io.github.coffeebreak.embedded.toxiproxy.core.ToxiproxyServer;
import io.github.coffeebreak.embedded.toxiproxy.core.ToxiproxyServer.ToxiproxyServerConfiguration;
import lombok.SneakyThrows;
import org.junit.rules.ExternalResource;

public class ToxiproxyServerRule extends ExternalResource {

  private final ToxiproxyServer toxiproxyServer;

  public ToxiproxyServerRule(ToxiproxyServerConfiguration configuration) {
    this.toxiproxyServer = new ToxiproxyServer(configuration);
  }

  @Override
  protected void before() throws Throwable {
    toxiproxyServer.start();
  }

  @SneakyThrows
  @Override
  protected void after() {
    toxiproxyServer.stop();
  }
}
