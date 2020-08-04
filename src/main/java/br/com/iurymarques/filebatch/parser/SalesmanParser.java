package br.com.iurymarques.filebatch.parser;

import br.com.iurymarques.filebatch.domain.salesman.Salesman;
import br.com.iurymarques.filebatch.exception.InvalidFormatException;

import java.util.regex.Matcher;

public class SalesmanParser extends Parser<Salesman> {
    private static final String SALESMAN_REGEX = "(001)ç([0-9]*)ç(.*)ç([+-]?([0-9]*[.])?[0-9]+)";

    public final int CPF_POSITION = 2;
    public final int NAME_POSITION = 3;
    public final int SALARY_POSITION = 4;

    @Override
    public Salesman parse(String line) throws InvalidFormatException {
        Matcher matcher = matcher(line);

        if (!matcher.find()) {
            throw new InvalidFormatException();
        }

        return new Salesman(
                readString(matcher, CPF_POSITION),
                readString(matcher, NAME_POSITION),
                readBigDecimal(matcher, SALARY_POSITION)
        );
    }

    @Override
    String format() {
        return SALESMAN_REGEX;
    }
}
