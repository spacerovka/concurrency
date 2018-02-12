package synchronization;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ComFutureExample implements Runnable {

    private CompletableFuture<String> result;

    public ComFutureExample(CompletableFuture<String> result) {
        this.result = result;
    }

    @Override
    public void run() {
        //simulate computing time
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result.complete("KARAS");
    }
}
