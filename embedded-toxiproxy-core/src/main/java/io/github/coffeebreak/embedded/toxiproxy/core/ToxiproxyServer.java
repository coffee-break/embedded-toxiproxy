package io.github.coffeebreak.embedded.toxiproxy.core;

import io.github.coffeebreak.embedded.toxiproxy.core.util.JarUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static org.apache.commons.lang3.SystemUtils.*;

@Slf4j
@RequiredArgsConstructor
public class ToxiproxyServer {

    @Getter
    private final ToxiproxyServerConfiguration configuration;

    public ToxiproxyServer() {
        this(ToxiproxyServerConfiguration.builder().build());
    }

    private Process process;

    public void start() throws Exception {
        String binaryName = String.format("2.1.4/toxiproxy-server-%s", findOsSuffix());
        File executablePath = JarUtil.extractExecutableFromJar(binaryName);

        process = new ProcessBuilder(executablePath.getCanonicalPath(), "-host", configuration.getHost(), "-port", String.valueOf(configuration.port))
                .start();

        Thread.sleep(configuration.timeout);
        log.info("Started embedded toxiproxy server");
    }

    public void stop() throws InterruptedException {
        log.info("Stopping embedded toxiproxy server");
        if (process!=null) {
            process.destroy();
            process.waitFor();
        }
    }

    private String findOsSuffix() {
        log.debug("Finding Os specific suffix for: {} and {}", OS_NAME, OS_ARCH);
        if (IS_OS_MAC) {
            return "darwin-amd64";
        } else if (IS_OS_LINUX) {
            return "linux-amd64";
        } else if (IS_OS_WINDOWS) {
            return "windows-amd64.exe";
        }

        throw new IllegalArgumentException("OS is not supported: " + OS_NAME);
    }

    @Getter
    @Builder
    public static class ToxiproxyServerConfiguration {
        @Getter
        @Builder.Default
        private String host = "localhost";

        @Getter
        @Builder.Default
        private int port = 8474;

        @Builder.Default
        private int timeout = 1000;
    }
}
