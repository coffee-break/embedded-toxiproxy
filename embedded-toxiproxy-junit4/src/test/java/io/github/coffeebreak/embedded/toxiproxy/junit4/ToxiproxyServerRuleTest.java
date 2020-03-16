package io.github.coffeebreak.embedded.toxiproxy.junit4;

import eu.rekawek.toxiproxy.ToxiproxyClient;
import io.github.coffeebreak.embedded.toxiproxy.core.ToxiproxyServer;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ToxiproxyServerRuleTest {

    @ClassRule
    public static ToxiproxyServerRule toxiproxyServerRule = new ToxiproxyServerRule(ToxiproxyServer.builder().timeout(5000));

    @Test
    public void shouldStartWithDefaultSettings() throws Exception {
        ToxiproxyClient client = new ToxiproxyClient();
        assertThat(client.version()).isEqualTo("2.1.4");
    }
}