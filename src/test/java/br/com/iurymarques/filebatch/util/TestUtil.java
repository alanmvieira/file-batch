package br.com.iurymarques.filebatch.util;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class TestUtil {

    private TestUtil() {
        // no-op
    }

    public static boolean deleteDirectory(String directory) throws IOException {
        Path path = Paths.get(directory);

        if (!Files.exists(path)) {
            return false;
        }

        FileUtils.cleanDirectory(path.toFile());
        Files.delete(path);

        return true;
    }

}
