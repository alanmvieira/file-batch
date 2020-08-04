package br.com.iurymarques.filebatch.parser;

import br.com.iurymarques.filebatch.domain.sale.Item;
import br.com.iurymarques.filebatch.domain.sale.Sale;
import br.com.iurymarques.filebatch.exception.InvalidFormatException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
public class SaleParserTest {

    @Test
    public void GIVEN_ValidLine_MUST_ReturnSale() throws InvalidFormatException {
        // given
        var line = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
        var items = new HashSet<Item>() {{
            add(new Item(1L, 10, new BigDecimal("100")));
            add(new Item(2L, 30, new BigDecimal("2.50")));
            add(new Item(3L, 40, new BigDecimal("3.10")));
        }};

        var expected = new Sale(10L, "Pedro", items);

        // when
        var parser = new SaleParser();
        var actual = parser.parse(line);

        // then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getSalesman()).isEqualTo(expected.getSalesman());
        assertThat(actual.getItems()).isEqualTo(expected.getItems());
    }

    @Test
    public void GIVEN_InvalidLine_MUST_ThrowException() {
        // given
        var line = "003ç10çPedro";

        // when
        Exception exception = assertThrows(InvalidFormatException.class, () -> {
            new CustomerParser().parse(line);
        });

        // then
        String expected = "The provided file has a invalid format input line";
        String actual = exception.getMessage();

        assertThat(actual).contains(expected);
    }
}
