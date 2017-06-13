package ua.com.serzh.completable_future.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergey on 6/8/17.
 */
public interface ExecutorUtil {

    <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit);

    ExecutorService createExecutor(int threads);

    void shutdownExecutor(ExecutorService executor);
}
