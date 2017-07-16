package ua.com.serzh.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

/**
 * @author sergii.zagryvyi on 15.07.2017
 */
public class CheckSome {

    private static String fromInt(Integer num) {
        System.out.println("fromInt, num: " + num);
        if (num == 2) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish " + num);
        return Integer.toString(num);
    }

    private static int sum(CompletableFuture<Integer> f, CompletableFuture<Integer> s) throws ExecutionException, InterruptedException {
        return f.get() + s.get();
    }

    public static void main(String[] args) {
        CompletableFuture<String> closing = new CompletableFuture<>();

        Stream<Integer> list = Stream.of(1, 2, 3);

       /* list
                .onClose(() -> closing.complete(""))
                .map(CompletableFuture::completedFuture)
//                .filter()
        .reduce(
                closing,
                (cf1, cf2) -> {
                    try {
                        return cf1.thenCombine(cf2, sum(cf1, cf2));
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );*/
        list.close();
    }




    /*@Bean(name = "timed")
    public Executor timeoutExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("timed-%d").build();
        return TimedCompletables.timed(Executors.newFixedThreadPool(10, threadFactory), Duration.ofSeconds(2));
    }

    @Async("timed")
    public CompletableFuture<String> asyncTimeoutGreeting() {
        AsyncUtil.randomSleep(3000, TimeUnit.MILLISECONDS);
        String result = AsyncUtil.getThreadName() + " - " + random(greetings);
        return CompletableFuture.completedFuture(result);
    }*/
}
