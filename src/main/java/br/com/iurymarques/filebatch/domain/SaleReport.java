package br.com.iurymarques.filebatch.domain;

import br.com.iurymarques.filebatch.domain.customer.Customer;
import br.com.iurymarques.filebatch.domain.sale.Sale;
import br.com.iurymarques.filebatch.domain.salesman.Salesman;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SaleReport {
    private static final String NEW_LINE = "\n";

    private Path path;

    private final Set<Sale> sales;
    private final Set<Salesman> salesmen;
    private final Set<Customer> customers;

    private final Map<String, BigDecimal> salesmanTotalSales;

    public SaleReport() {
        this.sales = new HashSet<>();
        this.salesmen = new HashSet<>();
        this.customers = new HashSet<>();
        this.salesmanTotalSales = new HashMap<>();
    }

    public SaleReport(Path path) {
        this.sales = new HashSet<>();
        this.salesmen = new HashSet<>();
        this.customers = new HashSet<>();
        this.salesmanTotalSales = new HashMap<>();

        this.path = path;
    }

    public void addSale(Sale sale) {
        sales.add(sale);
        addToSalesmanTotalSaleCost(sale.getSalesman(), sale.cost());
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addSalesman(Salesman salesman) {
        salesmen.add(salesman);
    }

    public Path getPath() {
        return path;
    }

    private void addToSalesmanTotalSaleCost(String salesman, BigDecimal cost) {
        BigDecimal previousTotalSaleCost = salesmanTotalSales.getOrDefault(salesman, BigDecimal.ZERO);
        BigDecimal newTotalSaleCost = previousTotalSaleCost.add(cost);


        salesmanTotalSales.put(salesman, newTotalSaleCost);
    }

    private Long mostExpensiveSale() {
        if (sales.isEmpty()) {
            return null;
        }

        Sale mostExpensiveSale = sales.stream()
                .reduce(Sale::mostExpensive)
                .get();

        return mostExpensiveSale.getId();
    }

    private String worstSalesman() {
        if (salesmanTotalSales.isEmpty()) {
            return null;
        }

        return salesmanTotalSales.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .get().getKey();
    }

    public String compile() {
        if (sales.isEmpty() || salesmen.isEmpty() || customers.isEmpty()) {
            return null;
        }

        return "Total customer count: " +
                customers.size() +
                NEW_LINE +
                "Total salesman count: " +
                salesmen.size() +
                NEW_LINE +
                "ID most expensive sale: " +
                mostExpensiveSale() +
                NEW_LINE +
                "Worst salesman: " +
                worstSalesman();
    }
}
