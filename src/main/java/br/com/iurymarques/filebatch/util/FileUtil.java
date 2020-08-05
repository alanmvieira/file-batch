package br.com.iurymarques.filebatch.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;

public final class FileUtil {

    private FileUtil() {
        // no-op
    }

    public static boolean isDatFile(Path path) {
        String filename = path.getFileName().toString();
        return filename.endsWith(".dat");
    }

    public static void createPublicDirectory(Path path) throws IOException {
        Files.createDirectories(path);
        setPublicPermission(path);
    }

    public static void writePublic(Path path, byte[] bytes) throws IOException {
        Files.write(path, bytes);
        setPublicPermission(path);
    }

    public static void setPublicPermission(Path path) throws IOException {
        Set<PosixFilePermission> perms = new HashSet<>();

        //add owners permission
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        //add group permissions
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_WRITE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        //add others permissions
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_WRITE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);

        Files.setPosixFilePermissions(path, perms);
    }
}
