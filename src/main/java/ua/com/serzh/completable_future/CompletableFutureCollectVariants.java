package ua.com.serzh.completable_future;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Sergey on 6/13/17.
 */
@Slf4j
public class CompletableFutureCollectVariants {

    private ExecutorService executor;

    private ScheduledThreadPoolExecutor delayer = new ScheduledThreadPoolExecutor(1);
    private long timeout = 5;

    /**
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @param <T>     the type of the task's result
     * @return a Future representing pending completion of the task
     * or TimeoutException if the task interrupted
     */
    public <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit) {
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
    private ExecutorService createExecutor(int threads) {
        return Executors.newFixedThreadPool(threads);
    }


    private Boolean getRouteDetails(List<String> segments) {
        int size = segments.size();
        ExecutorService executor = createExecutor(size);
        List<Boolean> result = segments.stream().map(segment ->
                CompletableFuture.supplyAsync(() -> getSegmentDetails(segment), executor)
                        .applyToEither(timeoutAfter(timeout, TimeUnit.SECONDS), Function.identity())
                        .exceptionally(error -> {
//                            log.warn("Failed getSegmentDetails: " + error);
                            return false;
                        }))
                .collect(Collectors.toList())
                .stream().map(CompletableFuture::join).collect(Collectors.toList());
        shutdownExecutor(executor);

        return !result.contains(false);
    }

    private boolean getSegmentDetails(String segment) {
        return true;
    }


    public String[] getVaraint(String criteria) {
        Set<Integer> searchEngines = new HashSet<>();
        ExecutorService executor = Executors.newFixedThreadPool(searchEngines.size());

        String[] routeVariants = searchEngines.stream().map(
                searchEngine -> CompletableFuture.supplyAsync(() -> search(criteria), executor)
                        .exceptionally(error -> {
//                        log.warn("Failed getSegmentDetails: " + error);
                            return new String[1];
                        }))
                .collect(Collectors.toList())
                .stream().map(CompletableFuture::join).collect(Collectors.toList())
                .stream().flatMap(Arrays::stream).toArray(String[]::new);

        executor.shutdown();

        return routeVariants;
    }

    public String[] search(String criteria) {
        String[] routeVariants = new String[5];
        routeVariants[1] = "";
        routeVariants[2] = "";
        routeVariants[3] = "";
        return routeVariants;
    }


    private void shutdownExecutor(ExecutorService executor) {
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
