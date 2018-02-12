package forkjoin;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public static List<Product> generate(int size) {
        List<Product> ret = new ArrayList<Product>();
        for (int i = 0; i < size; i++) {
            Product product = new Product("Product " + i, 10);
            ret.add(product);
        }
        return ret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
