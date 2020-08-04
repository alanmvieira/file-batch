package br.com.iurymarques.filebatch.parser;

import br.com.iurymarques.filebatch.domain.salesman.Salesman;
import br.com.iurymarques.filebatch.exception.InvalidFormatException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
public class SalesmanParserTest {

    @Test
    public void GIVEN_ValidLine_MUST_ReturnSalesman() throws InvalidFormatException {
        // given
        var line = "001ç3245678865434çPauloç40000.99";
        var expected = new Salesman("3245678865434", "Paulo", new BigDecimal("40000.99"));

        // when
        var actual = new SalesmanParser().parse(line);

        // then
        assertThat(actual.getCpf()).isEqualTo(expected.getCpf());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getSalary()).isEqualTo(expected.getSalary());
    }

    @Test
    public void GIVEN_InvalidLine_MUST_ThrowException() {
        // given
        var line = "001ç3245678865434çPaulo";

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
