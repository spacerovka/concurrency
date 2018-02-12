import forkjoin.NameResultsTask;
import forkjoin.PriceUpdateTask;
import forkjoin.Product;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestForkJoin {
    @Test
    public void priceUpdaterTest() {
        List<Product> products = Product.generate(10000);
        PriceUpdateTask task = new PriceUpdateTask(products, 0, products.size(), 0.20);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.execute(task);

        do {
            System.out.println("Thread Count: " + pool.getActiveThreadCount());
            System.out.println("Thread Steal: " + pool.getStealCount());
            System.out.println("Parallelism: " + pool.getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        pool.shutdown();

        if (task.isCompletedNormally()) {
            System.out.printf("Main: The process has completed normally.\n");
        }

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            assertEquals(12.0, product.getPrice(), 0);
        }

    }

    @Test
    public void nameResultTest() throws ExecutionException, InterruptedException {
        List<Product> products = Product.generate(100);
        NameResultsTask task = new NameResultsTask(products, 0, products.size());
        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.execute(task);

        do {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        pool.shutdown();

        pool.awaitTermination(1, TimeUnit.DAYS);

        assertEquals("[Product 0][Product 1][Product 2][Product 3][Product 4][Product 5][Product 6][Product 7][Product 8][Product 9][Product 10][Product 11][Product 12][Product 13][Product 14][Product 15][Product 16][Product 17][Product 18][Product 19][Product 20][Product 21][Product 22][Product 23][Product 24][Product 25][Product 26][Product 27][Product 28][Product 29][Product 30][Product 31][Product 32][Product 33][Product 34][Product 35][Product 36][Product 37][Product 38][Product 39][Product 40][Product 41][Product 42][Product 43][Product 44][Product 45][Product 46][Product 47][Product 48][Product 49][Product 50][Product 51][Product 52][Product 53][Product 54][Product 55][Product 56][Product 57][Product 58][Product 59][Product 60][Product 61][Product 62][Product 63][Product 64][Product 65][Product 66][Product 67][Product 68][Product 69][Product 70][Product 71][Product 72][Product 73][Product 74][Product 75][Product 76][Product 77][Product 78][Product 79][Product 80][Product 81][Product 82][Product 83][Product 84][Product 85][Product 86][Product 87][Product 88][Product 89][Product 90][Product 91][Product 92][Product 93][Product 94][Product 95][Product 96][Product 97][Product 98][Product 99]",
                task.get());

    }
}
