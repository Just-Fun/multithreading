package ua.com.serzh.completable_future.util.impl;

import lombok.extern.slf4j.Slf4j;
import ua.com.serzh.completable_future.util.ExecutorUtil;

import java.util.concurrent.*;

/**
 * Realization of executor service
 * @see ExecutorUtil
 */
@Slf4j
public class ExecutorUtilImpl implements ExecutorUtil {

    private ScheduledThreadPoolExecutor delayer = new ScheduledThreadPoolExecutor(1);

    /**
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @param <T> the type of the task's result
     * @return a Future representing pending completion of the task
     * or TimeoutException if the task interrupted
     */
    @Override
    public  <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit) {
        CompletableFuture<T> result = new CompletableFuture<>();
        delayer.schedule(() -> result.completeExceptionally(new TimeoutException("Timeout after " + timeout)), timeout, unit);
        return result;
    }

    /**
     * Creates a thread pool that reuses a fixed number of threads
     *
     * @param threads number of threads
     * @return the newly created thread pool
     */
    @Override
    public ExecutorService createExecutor(int threads) {
        return Executors.newFixedThreadPool(threads);
    }


    /**
     * Initiates an orderly shutdown
     *
     * @param executor ExecutorService to shutdown
     */
    @Override
    public void shutdownExecutor(ExecutorService executor) {
        try {
//            log.debug("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
//            log.warn("tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
//                log.warn("cancel non-finished tasks");
            }
            executor.shutdownNow();
//            log.debug("executor shutdown finished");
        }
    }

}
