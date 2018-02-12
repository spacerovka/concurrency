package collections;

import java.util.concurrent.ConcurrentLinkedDeque;

public class GetTask implements Runnable {

    private final ConcurrentLinkedDeque<String> list;

    public GetTask(ConcurrentLinkedDeque<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 5000; i++) {
            list.pollFirst();
            list.pollLast();
        }
    }
}
