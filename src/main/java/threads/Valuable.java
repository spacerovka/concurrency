package threads;

import java.util.Random;

public class Valuable implements Runnable{
    private static ThreadLocal<Integer> bitcoins = new ThreadLocal<Integer>(){
        protected Integer initialValue() {
            return 100;
        }
    };

    public static ThreadLocal<Integer> getBitcoins() {
        return bitcoins;
    }

    public static void setBitcoins(ThreadLocal<Integer> bitcoins) {
        Valuable.bitcoins = bitcoins;
    }

    public void run() {
        int result;
        Random random = new Random(Thread.currentThread().getId());
        while (true) {
            result = bitcoins.get() / ((int) (random.nextDouble()));
            System.out.printf("Bitcoin is changed to {} \n", result);
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("{} : Interrupted\n", Thread.currentThread().getId());
                return;
            }
        }
    }
}
