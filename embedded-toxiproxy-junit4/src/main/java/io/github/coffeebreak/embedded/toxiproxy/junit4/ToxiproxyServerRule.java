package io.github.coffeebreak.embedded.toxiproxy.junit4;

import io.github.coffeebreak.embedded.toxiproxy.core.ToxiproxyServer;
import io.github.coffeebreak.embedded.toxiproxy.core.ToxiproxyServer.ToxiproxyServerBuilder;
import lombok.SneakyThrows;
import org.junit.rules.ExternalResource;

public class ToxiproxyServerRule extends ExternalResource {

    private ToxiproxyServer toxiproxyServer;

    public ToxiproxyServerRule(ToxiproxyServerBuilder toxiproxyServerBuilder) {
        this.toxiproxyServer = toxiproxyServerBuilder.build();
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
