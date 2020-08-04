package br.com.iurymarques.filebatch.parser;

import br.com.iurymarques.filebatch.domain.customer.Customer;
import br.com.iurymarques.filebatch.exception.InvalidFormatException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("unit")
public class CustomerParserTest {

    @Test
    public void GIVEN_ValidLine_MUST_ReturnCustomer() throws InvalidFormatException {
        // given
        var line = "002ç2345675434544345çJose da SilvaçRural";
        var expected = new Customer("2345675434544345", "Jose da Silva", "Rural");

        // when
        var actual = new CustomerParser().parse(line);

        // then
        assertThat(actual.getCnpj()).isEqualTo(expected.getCnpj());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getBusinessArea()).isEqualTo(expected.getBusinessArea());
    }

    @Test
    public void GIVEN_InvalidLine_MUST_ThrowException() {
        // given
        var line = "002çJose da SilvaçRural";

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
