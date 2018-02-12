package forkjoin;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class NameResultsTask extends RecursiveTask<String> {

    private List<Product> products;
    private int first;
    private int last;

    public NameResultsTask(List<Product> products, int first, int last) {
        this.products = products;
        this.first = first;
        this.last = last;
    }

    @Override
    protected String compute() {
        String result = null;
        if (last - first < 10) {
            result = process(products, first, last);
        } else {
            int middle = (last + first) / 2;
            NameResultsTask t1 = new NameResultsTask(products, first, middle + 1);
            NameResultsTask t2 = new NameResultsTask(products, middle + 1, last);
            invokeAll(t1, t2);
            try {
                result = groupResults(t1.get(), t2.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String groupResults(String s, String s1) {
        return s + s1;
    }

    private String process(List<Product> products, int first, int last) {
        StringBuilder builder = new StringBuilder();
        for (int i = first; i < last; i++) {
            builder.append("[" + products.get(i).getName() + "]");
        }
        return builder.toString();
    }
}
