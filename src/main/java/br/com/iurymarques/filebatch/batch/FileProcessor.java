package br.com.iurymarques.filebatch.batch;

import br.com.iurymarques.filebatch.domain.SaleReport;
import br.com.iurymarques.filebatch.domain.customer.Customer;
import br.com.iurymarques.filebatch.domain.sale.Sale;
import br.com.iurymarques.filebatch.domain.salesman.Salesman;
import br.com.iurymarques.filebatch.exception.InvalidFormatException;
import br.com.iurymarques.filebatch.exception.NonDatFileException;
import br.com.iurymarques.filebatch.parser.CustomerParser;
import br.com.iurymarques.filebatch.parser.SaleParser;
import br.com.iurymarques.filebatch.parser.SalesmanParser;
import br.com.iurymarques.filebatch.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static br.com.iurymarques.filebatch.util.LambdaExceptionUtil.rethrowConsumer;

public class FileProcessor implements ItemProcessor<Path, SaleReport> {
    private static final Logger LOG = LoggerFactory.getLogger(FileProcessor.class);

    private static final String SALESMAN_ID = "001";
    private static final String CONSUMER_ID = "002";
    private static final String SALE_ID = "003";

    private SaleReport saleReport;

    @Override
    public SaleReport process(Path path) {
        saleReport = new SaleReport(path);

        try {
            LOG.info("ITEM PROCESSOR --> Processing file: {}", path.getFileName());
            readFileLines(path).forEach(rethrowConsumer(this::parse));

            return saleReport;
        } catch (Exception e) {
            LOG.error(e.getMessage());

            return saleReport;
        }
    }

    private void parse(String line) throws InvalidFormatException {
        if (isConsumer(line)) {
            Customer customer = new CustomerParser().parse(line);
            saleReport.addCustomer(customer);
        } else if (isSale(line)) {
            Sale sale = new SaleParser().parse(line);
            saleReport.addSale(sale);
        } else if (isSalesman(line)) {
            Salesman salesman = new SalesmanParser().parse(line);
            saleReport.addSalesman(salesman);
        } else {
            throw new InvalidFormatException();
        }
    }

    private boolean isConsumer(String line) {
        return line.startsWith(CONSUMER_ID);
    }

    private boolean isSale(String line) {
        return line.startsWith(SALE_ID);
    }

    private boolean isSalesman(String line) {
        return line.startsWith(SALESMAN_ID);
    }

    private Stream<String> readFileLines(Path path)
            throws IOException, NonDatFileException {

        if (FileUtil.isDatFile(path)) {
            return Files.lines(path);
        }

        throw new NonDatFileException();
    }
}
