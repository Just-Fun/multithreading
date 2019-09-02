package ua.com.serzh.fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ForkJoinPoolClass {

    static final int MAX_CAP = 0x7fff; // 32767

    public static void main(String[] args) {
        ForkJoinPool custom = new ForkJoinPool(2);
        custom.submit(() -> {
            int result = IntStream.range(0, 3)
                    .parallel()
                    .peek(it -> System.out.printf("Thread1 [%s] peek: %d\n", Thread.currentThread().getName(), it))
                    .sum();
            System.out.println("sum: " + result);
        });

        int result = IntStream.range(0, 3)
                .parallel()
                .peek(it -> System.out.printf("Thread [%s] peek: %d\n",
                        Thread.currentThread().getName(), it))
                .sum();
        System.out.println("sum: " + result);


//        parallel();
//        makeCommonPool();
//        getParallelismPlusOne();

    }

    private static void parallel() {
        int result = IntStream.range(0, 3)
                .parallel()
                .peek(it -> System.out.printf("Thread [%s] peek: %d\n",
                        Thread.currentThread().getName(), it))
                .sum();
        System.out.println("sum: " + result);
    }
//    Thread [ForkJoinPool.commonPool-worker-1] peek: 0
//    Thread [main] peek: 1
//    Thread [ForkJoinPool.commonPool-worker-0] peek: 2
//    sum: 3

    private static ForkJoinPool makeCommonPool() {
        int parallelism = -1;
        try {  // ignore exceptions in accessing/parsing properties
            String pp = System.getProperty
                    ("java.util.concurrent.ForkJoinPool.common.parallelism");
            if (pp != null)
                parallelism = Integer.parseInt(pp);
        } catch (Exception ignore) {
        }
        if (parallelism < 0 && // default 1 less than #cores
                (parallelism = Runtime.getRuntime().availableProcessors() - 1) <= 0)
            parallelism = 1;
        if (parallelism > MAX_CAP)
            parallelism = MAX_CAP;
//        return new ForkJoinPool(parallelism, factory, handler, LIFO_QUEUE,
//                "ForkJoinPool.commonPool-worker-");
        return ForkJoinPool.commonPool();
    }

    private static void getParallelismPlusOne() {
        final long ms = System.currentTimeMillis();
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println("Parallelism: " + commonPool.getParallelism());
        IntStream.range(0, commonPool.getParallelism() + 1).forEach((it) -> commonPool.submit(() -> {
            try {
                System.out.printf("[%d sec] [%s]: #%d start()\n",
                        TimeUnit.SECONDS.convert(System.currentTimeMillis() - ms, TimeUnit.MILLISECONDS),
                        Thread.currentThread().getName(), it);
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf("[%d sec] [%s]: #%d finish()\n",
                    TimeUnit.SECONDS.convert(System.currentTimeMillis() - ms, TimeUnit.MILLISECONDS),
                    Thread.currentThread().getName(), it);
        }));

        int result = IntStream.range(0, 3)
                .parallel()
                .peek(it -> System.out.printf("Thread [%s] peek: %d\n",
                        Thread.currentThread().getName(), it))
                .sum();
        System.out.println("sum: " + result);
        try {
            commonPool.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
