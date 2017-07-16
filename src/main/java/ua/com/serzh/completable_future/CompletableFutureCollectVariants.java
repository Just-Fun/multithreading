package ua.com.serzh.completable_future;

import lombok.extern.slf4j.Slf4j;
import ua.com.serzh.completable_future.util.ExecutorUtil;
import ua.com.serzh.completable_future.util.impl.ExecutorUtilImpl;

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

    private ExecutorUtil executorUtil = new ExecutorUtilImpl();
    private long timeout = 5;

    private Boolean getBooleanList(List<String> segments) {
        int size = segments.size();
        ExecutorService executor = executorUtil.createExecutor(size);
        List<Boolean> result = segments.stream().map(segment ->
                CompletableFuture.supplyAsync(() -> getSegmentDetails(segment), executor)
                        .applyToEither(executorUtil.timeoutAfter(timeout, TimeUnit.SECONDS), Function.identity())
                        .exceptionally(error -> {
//                            log.warn("Failed getSegmentDetails: " + error);
                            return false;
                        }))
                .collect(Collectors.toList())
                .stream().map(CompletableFuture::join).collect(Collectors.toList());
        executorUtil.shutdownExecutor(executor);

        return !result.contains(false);
    }

    private boolean getSegmentDetails(String segment) {
        return true;
    }


    public String[] getStringArray(String criteria) {
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
        return routeVariants;
    }
}
