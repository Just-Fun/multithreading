package ua.com.serzh.completable_future;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Serzh on 3/11/17.
 */
public class PlayWithCF2 {
    private static Logger log = Logger.getLogger(PlayWithCF2.class);
    private static final int TIMEOUT = 90_000;

    public static void main(final String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            final long startTime = System.nanoTime();
            final List<Future<?>> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                final Future<?> future = executorService.submit(new MyWorker(8));
                list.add(future);
            }

            log.info("Waiting for the workers to finish");

            executorService.shutdown();
            try {
                executorService.awaitTermination(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<Future<?>> futures = new ArrayList<>();
            for (Future<?> future : list) {
                if (future.isDone()) {

                }  else {
                    future.cancel(true);
                }

            }

            PlayWithCF2.method2(list, 5, TimeUnit.SECONDS);
            final long finishTime = System.nanoTime();
            log.info(String.format("Program finished after: %,d nano seconds", finishTime - startTime));

        } finally {
            executorService.shutdown();
        }
    }


    public static void method2(final List<Future<?>> list, final long timeout, final TimeUnit timeUnit) {
        long globalWaitTime = timeUnit.toNanos(timeout);
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        for (final Future<?> future : list) {
            final long waitStart = System.nanoTime();
//            try {
//                future.get(globalWaitTime, TimeUnit.NANOSECONDS);
            try {
                future.get();
            } catch (InterruptedException e) {
                future.cancel(true);
                log.warn("Failed: %s", e);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
//            } catch ( TimeoutException e) {
//                future.cancel(true);
//            } catch (final Exception e) {
//                log.warn("Failed: %s", e);
//            } finally {
//                final long timeTaken = System.nanoTime() - waitStart;
//                globalWaitTime = Math.max(globalWaitTime - timeTaken, 0);
//            }
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(globalWaitTime, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*executorService.shutdown();
executorService.awaitTermination(globalWaitTime, TimeUnit.NANOSECONDS);
then
if(future.isDone()) then do whatever, else future.cancel*/

    /*public static void method2(final List<Future<?>> list, final long timeout, final TimeUnit timeUnit) {
        long globalWaitTime = timeUnit.toNanos(timeout);

        for (final Future<?> future : list) {
            final long waitStart = System.nanoTime();
            try {
                future.get(globalWaitTime, TimeUnit.NANOSECONDS);
            } catch (final TimeoutException e) {
                future.cancel(true);
            } catch (final Exception e) {
                log.warn("Failed: %s", e);
            } finally {
                final long timeTaken = System.nanoTime() - waitStart;
                globalWaitTime = Math.max(globalWaitTime - timeTaken, 0);
            }
        }
    }*/
}
