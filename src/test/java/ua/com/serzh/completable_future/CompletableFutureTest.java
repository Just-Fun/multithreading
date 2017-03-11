package ua.com.serzh.completable_future;

/**
 * Created by Serzh on 3/11/17.
 */
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Function;


public class CompletableFutureTest {


    static public void main(String[] args) throws Exception {
        Random random = new Random(Instant.now().getNano());

//        CompletableFuture<Integer> number =CompletableFuture.supplyAsync(() -> task(random.nextInt(3000),2));
        CompletableFuture<Integer> number =CompletableFuture.supplyAsync(() -> task(1,2));

        final CompletableFuture<Integer> responseFuture = within(number, Duration.ofSeconds(2));
//        Integer integer = responseFuture.get(2, TimeUnit.SECONDS);
//        System.out.println(integer);
        CompletableFuture<Void> last = responseFuture
                .thenAccept(System.out::print)
                .exceptionally(throwable -> {
                    System.out.println("Unrecoverable error " + throwable);
                    return null;
                });

        last.thenRun(()-> System.exit(0));
    }

    public static <T> CompletableFuture<T> within(CompletableFuture<T> future, Duration duration) {
        final CompletableFuture<T> timeout = failAfter(duration);
        return future.applyToEither(timeout, Function.identity());
    }

    public static <T> CompletableFuture<T> failAfter(Duration duration) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        scheduler.schedule(() -> {
            final TimeoutException ex = new TimeoutException("Timeout after " + duration);
            return promise.completeExceptionally(ex);
        }, duration.toMillis(), TimeUnit.MILLISECONDS);
        return promise;
    }


    private static final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);


    private static Integer task(int time, int number) {
        try {
//            Thread.sleep(sleep);
            TimeUnit.SECONDS.sleep(time);
            System.out.println(Thread.currentThread().getName() + "with number " + number);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number;
    }
}
