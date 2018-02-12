package synchronization;

import java.util.concurrent.CountDownLatch;

public class CountDowlLatchExample_Conference implements Runnable {
    private final CountDownLatch controller;

    public CountDowlLatchExample_Conference(int number) {
        controller = new CountDownLatch(number);
    }

    public void arrive(String name) {
        System.out.printf("%s has arrived.", name);
        controller.countDown();
        System.out.printf("Conference: Waiting for %d people.\n", controller.getCount());
    }

    @Override
    public void run() {
        System.out.printf("Conference: Start waiting for: %d participants.\n", controller.getCount());
        try {
            // wait till getCount() is 0
            controller.await();
            System.out.printf("Conference: All the participants have come\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
