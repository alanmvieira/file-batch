package br.com.iurymarques.filebatch.domain;

import br.com.iurymarques.filebatch.domain.customer.Customer;
import br.com.iurymarques.filebatch.domain.sale.Item;
import br.com.iurymarques.filebatch.domain.sale.Sale;
import br.com.iurymarques.filebatch.domain.salesman.Salesman;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class SaleReportTest {
    private static final String NEW_LINE = "\n";

    @Test
    public void WHEN_CompileReport_MUST_ReturnCorrectReport() {
        var report = new SaleReport();

        // given a salesman exists
        var paulo = new Salesman("3245678865434", "Paulo", new BigDecimal("40000.99"));
        var pedro = new Salesman("123456789123", "Pedro", new BigDecimal("50000"));
        report.addSalesman(paulo);
        report.addSalesman(pedro);

        // and a customer exists
        var joaoSilva = new Customer("2345675434544345", "Jose da Silva", "Rural");
        report.addCustomer(joaoSilva);

        // and a sale exists
        var item = new Item(1L, 10, new BigDecimal("100"));
        var item2 = new Item(1L, 30, new BigDecimal("2.50"));
        var item3 = new Item(1L, 40, new BigDecimal("3.10"));

        var pedroSale = new Sale(2L, "Pedro", Set.of(item, item2, item3));

        var item4 = new Item(1L, 34, new BigDecimal("10"));
        var item5 = new Item(1L, 33, new BigDecimal("1.50"));
        var item6 = new Item(1L, 40, new BigDecimal("0.10"));

        var pauloSale = new Sale(1L, "Paulo", Set.of(item4, item5, item6));

        report.addSale(pedroSale);
        report.addSale(pauloSale);

        // when i compile a report
        String actual = report.compile();

        // then the result should be as follow
        var expected = "Total customer count: 1" +
                NEW_LINE +
                "Total salesman count: 2" +
                NEW_LINE +
                "ID most expensive sale: 2" +
                NEW_LINE +
                "Worst salesman: Paulo";

        assertThat(actual).isNotEmpty();
        assertThat(actual).isEqualTo(expected);
    }
}
