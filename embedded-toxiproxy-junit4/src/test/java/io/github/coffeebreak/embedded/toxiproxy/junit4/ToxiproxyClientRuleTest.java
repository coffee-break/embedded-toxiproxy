package io.github.coffeebreak.embedded.toxiproxy.junit4;

import eu.rekawek.toxiproxy.model.ToxicDirection;
import io.github.coffeebreak.embedded.toxiproxy.core.ToxiproxyServer;
import io.github.coffeebreak.embedded.toxiproxy.junit4.ToxiproxyClientRule.ProxyConfiguration;
import io.github.coffeebreak.embedded.toxiproxy.junit4.utils.SqlDatabaseServerRule;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ToxiproxyClientRuleTest {

    private final static ProxyConfiguration config = ProxyConfiguration.builder()
            .name("database")
            .host("localhost")
            .listenPort(9124)
            .upstreamPort(9123)
            .build();

    private static final ToxiproxyClientRule toxiproxyClientRule = new ToxiproxyClientRule(config);
    private static final SqlDatabaseServerRule embeddedDatabaseRule = new SqlDatabaseServerRule();

    @ClassRule
    public static TestRule databaseWithToxiproxy = RuleChain
            .outerRule(new ToxiproxyServerRule(ToxiproxyServer.builder()))
            .around(embeddedDatabaseRule)
            .around(toxiproxyClientRule);

    @After
    public void before() {
        toxiproxyClientRule.clearToxics();
    }

    @Test
    public void testUsingDirectConnectionUrl() throws Exception {
        String connectionUrl = "jdbc:h2:tcp://localhost:9123/~/mem;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";

        try(final Connection connection = DriverManager.getConnection(connectionUrl)) {
            try(final Statement statement = connection.createStatement();
                final ResultSet resultSet = statement.executeQuery("SELECT 1")
            ) {
                assertThat(resultSet.next()).isTrue();
            }
        }
    }

    @Test
    public void testUsingToxiproxyConnectionUrl() throws Exception {

        String connectionUrl = "jdbc:h2:tcp://localhost:9124/~/mem;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";

        try(final Connection connection = DriverManager.getConnection(connectionUrl)) {
            try(final Statement statement = connection.createStatement()) {
                toxiproxyClientRule.getProxy().toxics().timeout("mysql-timeout-toxic", ToxicDirection.DOWNSTREAM, 100);
                assertThatThrownBy(() -> {
                    try (final ResultSet resultSet = statement.executeQuery("SELECT 1") ) {
                        assertThat(resultSet.next()).isTrue();
                    }
                }).hasMessageContaining("Connection is broken: \"session closed\"");
            }
        }
    }
}