package executors;

import java.time.LocalDateTime;

public class Task implements Runnable {
    private final String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Task " + name + " is started " + LocalDateTime.now());
    }
}
