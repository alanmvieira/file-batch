package br.com.iurymarques.filebatch.batch;

import br.com.iurymarques.filebatch.domain.SaleReport;
import br.com.iurymarques.filebatch.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileWriter implements ItemWriter<SaleReport> {
    private static final Logger LOG = LoggerFactory.getLogger(FileWriter.class);

    @Value("${path.output}")
    private String outputDirectory;

    @Value("${path.success}")
    private String successDirectory;

    @Value("${path.error}")
    private String errorDirectory;

    @Override
    public void write(List<? extends SaleReport> list) throws Exception {
        for (SaleReport saleReport : list) {
            String report = saleReport.compile();

            if (report == null) {
                moveFileToDirectory(saleReport.getPath(), errorDirectory);
                break;
            }

            writeResultToOutputFolder(report, saleReport.getPath());
            moveFileToDirectory(saleReport.getPath(), successDirectory);
        }
    }

    private void writeResultToOutputFolder(String report, Path path) throws IOException {
        String filename = getDoneFilename(path);

        Path outputPath = Paths.get(outputDirectory);
        FileUtil.createPublicDirectory(outputPath);

        Path outputFile = outputPath.resolve(filename);

        LOG.info("ITEM WRITER --> Writing result fo file {}", outputFile);
        FileUtil.writePublic(outputFile, report.getBytes());
    }

    private void moveFileToDirectory(Path oldPath, String directory) throws IOException {
        Path newPath = Paths.get(directory);
        FileUtil.createPublicDirectory(newPath);

        String filename = oldPath.getFileName().toString();
        Path newFilePath = newPath.resolve(filename);

        LOG.info("ITEM WRITER --> Moving input to file {}", newFilePath);
        Files.move(oldPath, newFilePath, StandardCopyOption.REPLACE_EXISTING);
    }

    private String getDoneFilename(Path path) {
        String filename = path.getFileName().toString();
        String filenameWithoutExtension = filename.substring(0, filename.length() - 4);
        return filenameWithoutExtension + ".done.dat";
    }
}
