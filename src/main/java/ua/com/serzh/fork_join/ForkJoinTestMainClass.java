package ua.com.serzh.fork_join;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinTestMainClass {

    private static ForkJoinPool pool = new ForkJoinPool(7);

    public static void main(String[] args) {
       /* System.out.println(
                "counter=" + counter + "." + idx +
                        " activeThreads=" + pool.getActiveThreadCount() +
                        " runningThreads=" + pool.getRunningThreadCount() +
                        " poolSize=" + pool.getPoolSize() +
                        " queuedTasks=" + pool.getQueuedTaskCount() +
                        " queuedSubmissions=" + pool.getQueuedSubmissionCount() +
                        " parallelism=" + pool.getParallelism() +
                        " stealCount=" + pool.getStealCount());*/
        String workload = "checkmoreaksdhfgadfsdfdgfdhgfdhgfdhgfdhgfdgdgdssgads";
//        new CustomRecursiveAction(workload).invoke(); // use parallelism according to number of cores
        pool.invoke(new CustomRecursiveAction(workload)); //

    /*    int[] array = {15, 11, 14, 17};
        Integer invoke = new CustomRecursiveTask(array).invoke();
        Integer integer = new ForkJoinPool().invoke(new CustomRecursiveTask(array));
        System.out.println(invoke);
        System.out.println(integer);*/

     /*   int result = IntStream.range(0, 10)
                .parallel()
                .peek(it -> System.out.printf("Thread [%s] peek: %d\n", Thread.currentThread().getName(), it))
                .sum();
        System.out.println("sum: " + result);*/
    }

    /**/
}
