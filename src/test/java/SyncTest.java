import org.junit.Test;
import synchronization.ComFutureExample;
import synchronization.CountDowlLatchExample_Conference;
import synchronization.PhaserExample;
import synchronization.ReadWriteLockExample;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SyncTest {

    @Test
    public void testReadWriteLock() {
        final ReadWriteLockExample data = new ReadWriteLockExample();

        Thread writer = new Thread(() -> data.setMyLockedValue("NewValue"));

        Thread reader = new Thread(data::getMyLockedValue);

        try {
            writer.start();
            TimeUnit.SECONDS.sleep(1);
            reader.start();
            writer.join();
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //11 Start writing value
        // 12 Start reading value
        // 11 Finish writing value
        // 12 Finish reading value
    }

    @Test
    public void testConference() throws InterruptedException {
        int peopleCount = 10;
        CountDowlLatchExample_Conference taskConference = new CountDowlLatchExample_Conference(peopleCount);
        Thread conference = new Thread(taskConference);
        conference.start();
        for (int i = 0; i < peopleCount; i++) {
            taskConference.arrive("Person " + i);
        }
        TimeUnit.SECONDS.sleep(1);
        //conference thread is finished after all people arrived
        assertEquals("TERMINATED", conference.getState().name());
    }

    @Test
    public void testPhaser() {
        Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new PhaserExample((phaser))).start();
        }
//        12 has entered first phase
//        13 has entered first phase
//        11 has entered first phase
//        14 has entered first phase
//        15 has entered first phase
//        15 has entered second phase
//        12 has entered second phase
//        11 has entered second phase
//        14 has entered second phase
//        13 has entered second phase

    }

    @Test
    public void testComputableFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();
        Thread thread = new Thread(new ComFutureExample(future));
        thread.start();
        String generated = "";
        try {
            generated = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Main: The string is: " + generated);
        assertFalse(generated.isEmpty());
    }
}
