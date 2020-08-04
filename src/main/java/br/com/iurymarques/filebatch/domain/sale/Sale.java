package br.com.iurymarques.filebatch.domain.sale;

import java.math.BigDecimal;
import java.util.Set;

public class Sale {

    private Long id;
    private String salesman;
    private Set<Item> items;

    public Sale(Long id, String salesman, Set<Item> items) {
        this.id = id;
        this.salesman = salesman;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public String getSalesman() {
        return salesman;
    }

    public Set<Item> getItems() {
        return items;
    }

    public BigDecimal cost() {
        return items.stream()
                .map(Item::cost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Sale mostExpensive(Sale firstSale, Sale secondSale) {
        BigDecimal firstSaleCost = firstSale.cost();
        BigDecimal secondSaleCost = secondSale.cost();

        return firstSaleCost.compareTo(secondSaleCost) > 0 ? firstSale : secondSale;
    }
}
