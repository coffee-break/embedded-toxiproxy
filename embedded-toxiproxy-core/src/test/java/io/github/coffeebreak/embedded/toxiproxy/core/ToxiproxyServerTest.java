package io.github.coffeebreak.embedded.toxiproxy.core;

import eu.rekawek.toxiproxy.ToxiproxyClient;
import io.github.coffeebreak.embedded.toxiproxy.core.ToxiproxyServer.ToxiproxyServerConfiguration;
import org.junit.After;
import org.junit.Test;

import static io.github.coffeebreak.embedded.toxiproxy.core.utils.PortScavenger.getFreePort;
import static org.assertj.core.api.Assertions.assertThat;

public class ToxiproxyServerTest {

    private ToxiproxyServer toxiproxyServer;

    @After
    public void tearDown() throws InterruptedException {
        toxiproxyServer.stop();
    }

    @Test
    public void shouldStartWithDefaultSettings() throws Exception {
        toxiproxyServer = new ToxiproxyServer();
        toxiproxyServer.start();

        ToxiproxyClient client = new ToxiproxyClient();
        assertThat(client.version()).contains("2.7.0");
    }

    @Test
    public void shouldAllowToCustomiseHostAndPort() throws Exception {
        ToxiproxyServerConfiguration configuration = ToxiproxyServerConfiguration.builder()
                .host("localhost")
                .port(getFreePort())
                .timeout(1000)
                .inheritIO(true)
                .build();
        toxiproxyServer = new ToxiproxyServer(configuration);
        toxiproxyServer.start();

        ToxiproxyClient client = new ToxiproxyClient(toxiproxyServer.getConfiguration().getHost(), toxiproxyServer.getConfiguration().getPort());
        assertThat(client.version()).contains("2.7.0");
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void shouldAaFailForUnsupportedOperatingSystem() throws Exception {
//        System.setProperty("os.name", "unsupported");
//        toxiproxyServer = new ToxiproxyServer(ToxiproxyServerConfiguration.builder().build());
//        toxiproxyServer.start();
//    }
}