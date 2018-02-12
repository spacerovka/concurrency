import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;

import java.util.concurrent.LinkedTransferQueue;

public class MultithreadedTCTest extends MultithreadedTestCase {

    //Internally, when the MultithreadedTC library has to execute a test, first it executes the
    //initialize() method. Then it creates a thread per method that starts with the thread
    //keyword (in your example, the methods thread1(), thread2(), and thread3()). When
    //all the threads have finished their execution, it executes the finish() method. To execute
    //the test, you used the runOnce() method of the TestFramework class.

    private LinkedTransferQueue<String> queue;

    @Override
    public void initialize() {
        super.initialize();
        queue = new LinkedTransferQueue<String>();
        System.out.printf("Test: The test has been initialized\n");
    }

    public void thread1() throws InterruptedException {
        String ret = queue.take();
        System.out.printf("Thread 1: %s\n", ret);
    }

    public void thread2() throws InterruptedException {
        waitForTick(1);//control the order
        // The metronome of the MultithreadedTC library has an internal
        //counter. When all the threads are blocked, the library increments this counter to the next
        //number specified in the waitForTick() calls that are blocked
        String ret = queue.take();
        System.out.printf("Thread 2: %s\n", ret);
    }

    public void thread3() {
        waitForTick(1);
        waitForTick(2);
        queue.put("Event 1");
        queue.put("Event 2");
        System.out.printf("Thread 3: Inserted two elements\n");
    }

    public void finish() {
        super.finish();
        assertEquals(true, queue.size() == 0);
    }

    public static void main(String[] args) throws Throwable {
        TestFramework.runOnce(new MultithreadedTCTest());
    }

}
