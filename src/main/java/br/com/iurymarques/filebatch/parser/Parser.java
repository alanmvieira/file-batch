package br.com.iurymarques.filebatch.parser;

import br.com.iurymarques.filebatch.exception.InvalidFormatException;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Parser<T> {

    abstract T parse(String line) throws InvalidFormatException;

    abstract String format();

    protected long readLong(Matcher matcher, int position) {
        String value = matcher.group(position);
        return value.isEmpty() ? null : Long.parseLong(value);
    }

    protected String readString(Matcher matcher, int position) {
        String value = matcher.group(position);
        return value.isEmpty() ? null : value;
    }

    protected BigDecimal readBigDecimal(Matcher matcher, int position) {
        String value = matcher.group(position);
        return value.isEmpty() ? null : new BigDecimal(value);
    }

    protected Matcher matcher(String value) {
        return Pattern.compile(format()).matcher(value);
    }
}
