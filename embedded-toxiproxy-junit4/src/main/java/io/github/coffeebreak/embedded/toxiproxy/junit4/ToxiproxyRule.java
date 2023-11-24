package io.github.coffeebreak.embedded.toxiproxy.junit4;

import io.github.coffeebreak.embedded.toxiproxy.core.ToxiproxyServer.ToxiproxyServerConfiguration;
import io.github.coffeebreak.embedded.toxiproxy.junit4.ToxiproxyClientRule.ProxyConfiguration;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ToxiproxyRule implements TestRule {

  private final ToxiproxyServerRule server;
  private final ToxiproxyClientRule client;
  private final TestRule target;

  public ToxiproxyRule(ProxyConfiguration proxyConfiguration, TestRule target) {
    this(ToxiproxyServerConfiguration.builder().build(), proxyConfiguration, target);
  }

  public ToxiproxyRule(
      ToxiproxyServerConfiguration configuration,
      ProxyConfiguration proxyConfiguration,
      TestRule target) {
    this.server = new ToxiproxyServerRule(configuration);
    this.client = new ToxiproxyClientRule(proxyConfiguration);
    this.target = target;
  }

  @Override
  public Statement apply(Statement base, Description description) {
    return RuleChain.outerRule(server).around(target).around(client).apply(base, description);
  }

  public ToxiproxyClientRule getClient() {
    return client;
  }
}
