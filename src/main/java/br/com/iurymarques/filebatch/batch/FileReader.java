package br.com.iurymarques.filebatch.batch;

import br.com.iurymarques.filebatch.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class FileReader implements ItemReader<Path> {
    private static final Logger LOG = LoggerFactory.getLogger(FileReader.class);

    @Value("${path.input}")
    private String inputDirectory;

    @Override
    public Path read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        LOG.info("FILE READER --> Reading files inside {}", inputDirectory);

        Path path = Paths.get(inputDirectory);
        FileUtil.createPublicDirectory(path);

        try (Stream<Path> stream = Files.walk(path)) {
            return stream.filter(not(Files::isDirectory))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .map(path::resolve)
                    .findFirst()
                    .orElse(null);
        }
    }
}
