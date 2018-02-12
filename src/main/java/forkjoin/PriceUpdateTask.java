package forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class PriceUpdateTask extends RecursiveAction {

    private List<Product> products;
    private int first;
    private int last;
    private double increment;

    public PriceUpdateTask(List<Product> products, int first, int last, double increment) {
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrices();
        } else {
            int middle = (last + first) / 2;
            PriceUpdateTask t1 = new PriceUpdateTask(products, first, middle + 1, increment);
            PriceUpdateTask t2 = new PriceUpdateTask(products, middle + 1, last, increment);
            invokeAll(t1, t2);
        }
    }

    private void updatePrices() {
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (1 + increment));
        }
    }
}
