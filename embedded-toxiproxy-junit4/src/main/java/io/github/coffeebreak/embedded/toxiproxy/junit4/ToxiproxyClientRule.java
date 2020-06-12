package io.github.coffeebreak.embedded.toxiproxy.junit4;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.Toxic;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.rules.ExternalResource;

@Getter
public class ToxiproxyClientRule extends ExternalResource {

    private final ProxyConfiguration proxyConfiguration;
    private final ToxiproxyClient client;

    private Proxy proxy;

    @SneakyThrows
    public
    ToxiproxyClientRule(ProxyConfiguration proxyConfiguration) {
        this.proxyConfiguration = proxyConfiguration;
        this.client = new ToxiproxyClient();
    }

    @SneakyThrows
    @Override
    protected void before() {
        this.proxy = client.createProxy(proxyConfiguration.getName(),
                String.format("%s:%s", proxyConfiguration.getHost(), proxyConfiguration.getListenPort()),
                String.format("%s:%s", proxyConfiguration.getHost(), proxyConfiguration.getUpstreamPort()));
    }

    @SneakyThrows
    @Override
    protected void after() {
        this.proxy.delete();
    }

    @SneakyThrows
    public void clearToxics() {
        this.proxy.toxics().getAll().forEach(this::removeToxic);
    }

    @SneakyThrows
    private void removeToxic(Toxic toxic) {
        toxic.remove();
    }

    @Getter
    @Builder
    public static class ProxyConfiguration {
        private String name;
        @Builder.Default
        private String host = "localhost";
        private int listenPort;
        private int upstreamPort;
    }
}
