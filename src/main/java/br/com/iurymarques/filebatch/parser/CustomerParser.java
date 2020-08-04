package br.com.iurymarques.filebatch.parser;

import br.com.iurymarques.filebatch.domain.customer.Customer;
import br.com.iurymarques.filebatch.exception.InvalidFormatException;

import java.util.regex.Matcher;

public class CustomerParser extends Parser<Customer> {
    private static final String CUSTOMER_REGEX = "(002)ç([0-9]{14,16})ç(.*)ç(.*)";

    public final int CNPJ_POSITION = 2;
    public final int NAME_POSITION = 3;
    public final int BUSINESS_AREA = 4;

    @Override
    public Customer parse(String line) throws InvalidFormatException {
        Matcher matcher = matcher(line);

        if (!matcher.find()) {
            throw new InvalidFormatException();
        }

        return new Customer(
                readString(matcher, CNPJ_POSITION),
                readString(matcher, NAME_POSITION),
                readString(matcher, BUSINESS_AREA)
        );
    }

    @Override
    String format() {
        return CUSTOMER_REGEX;
    }
}
