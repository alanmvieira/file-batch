package br.com.iurymarques.filebatch.domain.sale;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {

    private Long id;
    private Integer quantity;
    private BigDecimal price;

    public Item(Long id, Integer quantity, BigDecimal price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public BigDecimal cost() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                quantity == item.quantity &&
                Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price);
    }
}
