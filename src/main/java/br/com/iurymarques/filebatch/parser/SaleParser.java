package br.com.iurymarques.filebatch.parser;

import br.com.iurymarques.filebatch.domain.sale.Item;
import br.com.iurymarques.filebatch.domain.sale.Sale;
import br.com.iurymarques.filebatch.exception.InvalidFormatException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class SaleParser extends Parser<Sale> {
    private static final String SALE_REGEX = "(003)ç([0-9]*)ç(\\[.*\\])ç(\\w*)";
    private static final String BRACKETS_REGEX = "[\\[\\]]";

    private static final int SALE_ID_POSITION = 2;
    private static final int SALE_ITEMS_POSITION = 3;
    private static final int SALE_SALESMAN_POSITION = 4;

    private static final int ITEM_ID_POSITION = 0;
    private static final int ITEM_QUANTITY_POSITION = 1;
    private static final int ITEM_PRICE_POSITION = 2;

    private static final String DASH = "-";
    private static final String COMMA = ",";
    private static final String EMPTY = "";

    @Override
    public Sale parse(String line) throws InvalidFormatException {
        Matcher matcher = matcher(line);

        if (!matcher.find()) {
            throw new InvalidFormatException();
        }

        Set<Item> items = parseItems(readString(matcher, SALE_ITEMS_POSITION));

        return new Sale(
                readLong(matcher, SALE_ID_POSITION),
                readString(matcher, SALE_SALESMAN_POSITION),
                items
        );
    }

    @Override
    String format() {
        return SALE_REGEX;
    }

    private Set<Item> parseItems(String line) {
        String normalizedLine = line.replaceAll(BRACKETS_REGEX, EMPTY);

        String[] items = normalizedLine.split(COMMA);

        return Arrays.stream(items)
                .map(this::parseItem)
                .collect(Collectors.toSet());
    }

    private Item parseItem(String line) {
        String[] data = line.split(DASH);

        return new Item(
                Long.parseLong(data[ITEM_ID_POSITION]),
                Integer.parseInt(data[ITEM_QUANTITY_POSITION]),
                new BigDecimal(data[ITEM_PRICE_POSITION])
        );
    }
}
