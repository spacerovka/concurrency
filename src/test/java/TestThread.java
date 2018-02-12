import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import threads.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TestThread {

    @Test
    public void testLifecycle() throws InterruptedException {
        Thread karas = new Swimer();
        karas.setName("KARAS");
        assertEquals("NEW", karas.getState().name());

        karas.start();
        assertEquals("RUNNABLE", karas.getState().name());

        // karas.wait() make it WAITING and need to be used in synchronized block
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        karas.interrupt();

        karas.join();
        assertEquals("TERMINATED", karas.getState().name());
    }

    @Spy
    private ExceptionHandler handler;

    @Test
    public void testExceptionInThread() throws InterruptedException {
        Runnable exceptioner = new Exeptioner();
        Thread thread = new Thread(exceptioner);
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        verify(handler, times(1)).uncaughtException(any(Thread.class), any(Throwable.class));
        thread.join();
        assertEquals("TERMINATED", thread.getState().name());
    }

    @Test
    public void testGroup() {
        int numberOfThreads = 2 * Runtime.getRuntime().availableProcessors();
        HandledGroup threadGroup = new HandledGroup("BitcoinsGroup");
        Valuable task = new Valuable();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread t = new Thread(threadGroup, task);
            t.start();
        }
        assertEquals(2 * Runtime.getRuntime().availableProcessors(), threadGroup.activeCount());
    }
}
