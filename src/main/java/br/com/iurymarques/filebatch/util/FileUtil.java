package br.com.iurymarques.filebatch.util;

import java.nio.file.Path;

public final class FileUtil {

    private FileUtil() {
        // no-op
    }

    public static boolean isDatFile(Path path) {
        String filename = path.getFileName().toString();
        return filename.endsWith(".dat");
    }
}
