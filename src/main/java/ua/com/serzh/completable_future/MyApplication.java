package ua.com.serzh.completable_future;

import java.util.concurrent.*;

/**
 * Created by Serzh on 3/11/17.
 */
class MyApplication {
    public static Integer getValue() {
        System.out.println("I am called");

        // Simulating a long network call of 1 second in the worst case
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 10;
    }

   /* ScheduledExecutorService scheduledService =
            Executors.newScheduledThreadPool(5);

    ScheduledFuture scheduledFuture =
            scheduledService.schedule((Callable) () -> {
                        System.out.println("Executed!");
                        return "Called!";
                    },
                    5,
                    TimeUnit.SECONDS);*/

    public static void main() {
        ScheduledExecutorService scheduledService =
                Executors.newScheduledThreadPool(5);

        ExecutorService executor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                // This is an unbounded Queue. This should never be used
                // in real life. That is the first step to failure.
                new LinkedBlockingQueue<Runnable>());

        // We want to call the dummy service 10 times
        CompletableFuture<Object>[] allFutures = new CompletableFuture[10];
        for (int i = 0; i < 10; ++i) {
            allFutures[i] = CompletableFuture.supplyAsync(() -> {
                // Instead of using CompletableFuture.supplyAsync, directly create a future from the executor
                Future future = scheduledService.submit(() -> getValue());
                scheduledService.schedule(() -> future.cancel(true), 100, TimeUnit.MILLISECONDS);

                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException | CancellationException e) {
                    // pass
                }
                // You can choose to return a dummy value here
                return null;
            });
        }

        // Finally wait for all futures to join
        CompletableFuture.allOf(allFutures).join();
        System.out.println("All futures completed");
        System.out.println(executor.toString());
    }
}
