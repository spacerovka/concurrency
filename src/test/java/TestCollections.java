import collections.AddTask;
import collections.DelayedTask;
import collections.EventWithDelay;
import collections.GetTask;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static org.junit.Assert.assertEquals;

public class TestCollections {

    @Test
    public void testConcurrentLinkedDeque() {
        ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<>();
        List<Thread> addTAsks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new AddTask(list));
            addTAsks.add(thread);
            thread.start();
        }

        addTAsks.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        List<Thread> removeTasks = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new GetTask(list));
            removeTasks.add(thread);
            thread.start();
        }

        removeTasks.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertEquals(0, list.size());
    }


    @Test
    public void testDelayQueue() throws InterruptedException {
        DelayQueue<EventWithDelay> queue = new DelayQueue<>();

        List<Thread> tasks = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            tasks.add(new Thread(new DelayedTask(i + 1, queue)));
        }

        tasks.forEach(Thread::start);
        //each task add 100 events

        for (Thread task : tasks) {
            task.join();
        }

        do {
            int counter = 0;
            EventWithDelay event;
            do {
                event = queue.poll();
                if (event != null) counter++;
            } while (event != null);
            System.out.println(LocalDateTime.now().toLocalTime() + " read " + counter);
            TimeUnit.MILLISECONDS.sleep(500);
        } while (queue.size() > 0);

//        Thread 2: 2018-02-12T22:19:17.878
//        Thread 3: 2018-02-12T22:19:19.878
//        Thread 1: 2018-02-12T22:19:15.878
//        Thread 5: 2018-02-12T22:19:23.878
//        Thread 4: 2018-02-12T22:19:21.878
//        22:19:13.914 read 0
//        22:19:14.415 read 0
//        22:19:14.916 read 0
//        22:19:15.417 read 0
//        22:19:15.929 read 100
//        22:19:16.430 read 0
//        22:19:16.931 read 0
//        22:19:17.431 read 0
//        22:19:17.938 read 100
//        22:19:18.439 read 0
//        22:19:18.940 read 0
//        22:19:19.441 read 0
//        22:19:19.946 read 100
//        22:19:20.447 read 0
//        22:19:20.948 read 0
//        22:19:21.449 read 0
//        22:19:21.951 read 100
//        22:19:22.452 read 0
//        22:19:22.953 read 0
//        22:19:23.454 read 0
//        22:19:23.955 read 100

    }

    @Test
    public void testAtomicArray() {
        final int THREADS = 100;
        AtomicIntegerArray array = new AtomicIntegerArray(1000);

        Thread threadIncrementer[] = new Thread[THREADS];
        Thread threadDecrementer[] = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            threadIncrementer[i] = new Thread(() -> {
                for (int i1 = 0; i1 < array.length(); i1++) {
                    array.getAndIncrement(i1);
                }
            });
            threadDecrementer[i] = new Thread(() -> {
                for (int i1 = 0; i1 < array.length(); i1++) {
                    array.getAndDecrement(i1);
                }
            });
            threadIncrementer[i].start();
            threadDecrementer[i].start();
        }

        for (int i = 0; i < 100; i++) {
            try {
                threadIncrementer[i].join();
                threadDecrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < array.length(); i++) {
            assertEquals(0, array.get(i));
        }
    }
}
