package collections;

import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;

public class DelayedTask implements Runnable {

    private final int id;
    private final DelayQueue<EventWithDelay> queue;

    public DelayedTask(int id, DelayQueue<EventWithDelay> queue) {
        this.id = id;
        this.queue = queue;
    }

    @Override
    public void run() {
        LocalDateTime delay = LocalDateTime.now().plusSeconds(id * 2);
        System.out.printf("Thread %s: %s\n", id, delay);
        for (int i = 0; i < 100; i++) {
            EventWithDelay event = new EventWithDelay(delay);
            queue.add(event);
        }
    }
}
