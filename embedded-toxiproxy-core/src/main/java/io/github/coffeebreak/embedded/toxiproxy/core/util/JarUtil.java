package io.github.coffeebreak.embedded.toxiproxy.core.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JarUtil {

    public static File extractExecutableFromJar(String executable) throws IOException {
        File tmpDir = FileUtils.getTempDirectory();
        tmpDir.deleteOnExit();
        File command = new File(tmpDir, executable);

        ClassLoader classLoader = JarUtil.class.getClassLoader();
        URL resource = classLoader.getResource(executable);
        FileUtils.copyURLToFile(resource, command);
        command.deleteOnExit();
        command.setExecutable(true);

        return command;
    }
}
