package io.github.coffeebreak.embedded.toxiproxy.core;

import eu.rekawek.toxiproxy.ToxiproxyClient;
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
        toxiproxyServer = ToxiproxyServer.builder().build();
        toxiproxyServer.start();

        ToxiproxyClient client = new ToxiproxyClient();
        assertThat(client.version()).isEqualTo("2.1.4");
    }

    @Test
    public void shouldAllowToCustomiseHostAndPort() throws Exception {
        toxiproxyServer = ToxiproxyServer.builder().host("localhost").port(getFreePort()).timeout(100).build();
        toxiproxyServer.start();

        ToxiproxyClient client = new ToxiproxyClient(toxiproxyServer.getHost(), toxiproxyServer.getPort());
        assertThat(client.version()).isEqualTo("2.1.4");
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void shouldAaFailForUnsupportedOperatingSystem() throws Exception {
//        System.setProperty("os.name", "unsupported");
//        toxiproxyServer = ToxiproxyServer.builder().build();
//        toxiproxyServer.start();
//    }
}