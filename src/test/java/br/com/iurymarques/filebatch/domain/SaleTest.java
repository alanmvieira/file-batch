package br.com.iurymarques.filebatch.domain;

import br.com.iurymarques.filebatch.domain.sale.Item;
import br.com.iurymarques.filebatch.domain.sale.Sale;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
public class SaleTest {
    @Test
    public void WHEN_GetCost_MUST_ReturnSumOfAllItemsCost() {
        // given a list of items
        var firstItem = new Item(
                1L,
                10,
                new BigDecimal("1.55"));

        var secondItem = new Item(
                2L,
                5,
                new BigDecimal("5.00"));

        // and a sale with those items
        var sale = new Sale(1L, "Paulo", Set.of(firstItem, secondItem));

        // when i calculate the cost
        var actual = sale.cost();

        // then i expect the correct sum
        var expected = new BigDecimal("40.50");
        assertThat(actual).isEqualTo(expected);
    }
}
