package ua.com.serzh.presentations.multithreading.util.concurrent;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: viktor
 * Date: 11/23/14
 * Time: 4:51 PM
 */
public class ExecutorServiceDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        System.out.println("Running main thread " + Thread.currentThread().getName());
//        runSingleThread();
//        if (true) return;
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        FutureTask<String> futureTask1 = new FutureTask<>(new SimpleCallable(1000));
        executorService.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<>(new SimpleCallable(3000));
        executorService.execute(futureTask2);

        Future<String> futureTask3 = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new UnsupportedOperationException();
            }
        });

        while (true) {
            try {
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Done");
                    //shut down executor service
                    executorService.shutdown();
                    System.out.println(futureTask3.get());
                    return;
                }

                if (!futureTask1.isDone()) {
                    //wait indefinitely for future task to complete
                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    System.out.println("FutureTask2 output=" + s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }
        }

    }


    private static void runSingleThread() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new SimpleRunnable());
        executor.shutdown();
    }

    private static class SimpleRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello from parallel thread " + Thread.currentThread().getName());
        }
    }

    private static class SimpleCallable implements Callable<String> {

        private long waitTime;

        private SimpleCallable(long waitTime) {
            this.waitTime = waitTime;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(waitTime);
            return Thread.currentThread().getName();
        }
    }

}
