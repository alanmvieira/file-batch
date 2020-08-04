package br.com.iurymarques.filebatch.domain;

import br.com.iurymarques.filebatch.domain.sale.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemTest {

    @Test
    public void WHEN_GetCost_MUST_ReturnPriceMultipliedByQuantity() {
        // given a item
        var item = new Item(
                1L,
                10,
                new BigDecimal("1.55")
        );

        // when i calculate the cost
        var actual = item.cost();

        // then i expect the correct result
        var expected = new BigDecimal("15.50");
        assertThat(actual).isEqualTo(expected);
    }
}
