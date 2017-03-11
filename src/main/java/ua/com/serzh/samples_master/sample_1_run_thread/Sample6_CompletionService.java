package ua.com.serzh.samples_master.sample_1_run_thread;

import java.util.Random;
import java.util.concurrent.*;

import static ua.com.serzh.samples_master.ThreadUtils.print;
import static ua.com.serzh.samples_master.ThreadUtils.sleep;

// thanks http://blog.teamlazerbeez.com/2009/04/29/java-completionservice/
public class Sample6_CompletionService {

    static class MyCallable implements Callable {

        @Override
        public Object call() {
            int count = new Random().nextInt(10);
            while (--count > 0) {
                print("Я тут!");

                sleep(500);
            }
            return Thread.currentThread().getId();
        }
    }

    public static void main(String[] args) throws Exception {
        Callable task1 = new MyCallable();
        Callable task2 = new MyCallable();

        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService completion = new ExecutorCompletionService(executor);

        completion.submit(task1);
        completion.submit(task2);

        int remainingFutures = 2;
        while (remainingFutures > 0) {
            Future completed = completion.take();

            try {
              print("Finished: " + completed.get());
                remainingFutures--;
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }

}
