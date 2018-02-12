import executors.CallableTask;
import executors.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class TestExecutor {

    @Test(expected = RejectedExecutionException.class)
    public void basicExecutorTest() throws InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < 100; i++) {
            Task task = new Task("Task " + i);
            executor.execute(task);
            System.out.println(executor.getActiveCount());
        }
        executor.awaitTermination(1, TimeUnit.SECONDS);
        assertEquals(100, executor.getCompletedTaskCount());
        executor.shutdown();
        executor.execute(new Task("To be rejected"));
    }

    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(6);
        List<Future<String>> resultList = new ArrayList<>();
        List<String> names = Arrays.asList("Tester", "Developer", "PM");
        for (String name : names) {
            CallableTask task = new CallableTask(name);
            Future<String> result = executor.submit(task);
            resultList.add(result);
        }
        do {
            System.out.println("Wait for all completed");
        } while (executor.getCompletedTaskCount() < resultList.size());

        for (int i = 0; i < resultList.size(); i++) {
            System.out.println(resultList.get(i).get());
        }
        executor.shutdown();
    }
}
