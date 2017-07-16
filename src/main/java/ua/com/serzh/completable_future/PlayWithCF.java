package ua.com.serzh.completable_future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Serzh on 3/11/17.
 */
public class PlayWithCF {
    private Logger log = Logger.getLogger(PlayWithCF.class);

    //    final CompletableFuture<Response> oneSecondTimeout = failAfter(Duration.ofSeconds(1));

    // 1-st variant
    private ScheduledThreadPoolExecutor delayer = new ScheduledThreadPoolExecutor(2);

//    private CompletableFuture<Cello> timeoutAfter(long timeout, TimeUnit unit) {
    private <T> CompletableFuture<T> timeoutAfter(long timeout, TimeUnit unit) {
//        CompletableFuture<Cello> result = new CompletableFuture<>();
        CompletableFuture<T> result = new CompletableFuture<>();
        delayer.schedule(() -> result.completeExceptionally(new TimeoutException("Timeout after " + timeout)), timeout, unit);
        return result;
    }

    // 2-nd variant
    private /*static final*/ final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(
//                    TODO understand what does it means
                    1,
                    new ThreadFactoryBuilder()
                            .setDaemon(true)
                            .setNameFormat("failAfter-%d")
                            .build());

    public /*static*/ <T> CompletableFuture<T> failAfter(Duration duration) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        scheduler.schedule(() -> {
            final TimeoutException ex = new TimeoutException("Timeout after " + duration);
            return promise.completeExceptionally(ex);
        }, duration.toMillis(), MILLISECONDS);
        return promise;
    }


    public /*static*/ <T> CompletableFuture<T> within(CompletableFuture<T> future, Duration duration) {
        final CompletableFuture<T> timeout = failAfter(duration);
        return future.applyToEither(timeout, Function.identity());
    }

   /* public static ExecutorService es = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });*/

    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));

        CompletableFuture<List<T>> listCompletableFuture = allDoneFuture.thenApply(v ->
                futures.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.<T>toList())
        );
        return listCompletableFuture;
    }


    public Integer getCellos3() {
        /*ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

//        TimeUnit.MILLISECONDS.sleep(1337);

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("Remaining Delay: %sms", remainingDelay);*/


        ExecutorService executor = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                () -> "task1",
                () -> "task2",
                () -> "task3");

        try {
            executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 1;
    }

    public Integer getCellos2() {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<Integer> future = executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                return 123;
            } catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });

        Integer integer = 0;
        try {
            integer = future.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        return integer;
    }

    public List<Cello> getCellos(List<Cello> celloSet) {
      /*  ExecutorService executor = Executors.newFixedThreadPool(Math.min(celloSet.size(), 2), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });*/
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        ExecutorService executor = Executors.newFixedThreadPool(2);
//        final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
//        executorService = new ThreadPoolExecutor(n, n, 0L, TimeUnit.MILLISECONDS, queue);
//        ExecutorService executor = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.SECONDS, queue);

//        ExecutorService executor = new ThreadPoolExecutor(2, 2, 0L,
//                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
//        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Math.min(celloSet.size(), 2));

       /* ScheduledExecutorService sExecutor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
        ScheduledFuture<?> future = sExecutor.schedule(task, 3, TimeUnit.SECONDS);

        List<Cello> collect = celloSet.parallelStream().map(this::addAge)
                .collect(Collectors.toList());*/


//        ExecutorService executor = Executors.newFixedThreadPool(Math.min(celloSet.size(), 2));

      /*  ScheduledExecutorService scheduledService = Executors.newScheduledThreadPool(5);
        List<CompletableFuture<Object>> allFutures = new ArrayList<>();

        for (Cello cello : celloSet) {
            allFutures.add(CompletableFuture.supplyAsync(() -> {
                // Instead of using CompletableFuture.supplyAsync, directly create a future from the executor
                Future future = scheduledService.submit(() -> addAge(cello));
                scheduledService.schedule(() -> future.cancel(true), 2, TimeUnit.SECONDS);

                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException | CancellationException e) {
                    // pass
                }
                // You can choose to return a dummy value here
                return null;
            }));
        }*/

/*        List<Object> collect = allFutures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
//                .filter(c -> c.getMaster() != null)
                .collect(Collectors.toList());*/
/*
        BigDecimal result =
                CompletableFuture.supplyAsync(() -> findBestPrice("LDN - NYC"), executorService)
                        .thenCombine(CompletableFuture.supplyAsync(() -> queryExchangeRateFor("GBP")),
                                this::convert)
                        .get(1, TimeUnit.SECONDS);
*/

         /*CompletableFuture<Cello> responseFuture = within(
                asyncCode(), Duration.ofSeconds(1));
        responseFuture
                .thenAccept(this::addAge)
                .exceptionally(throwable -> {
                    log.error("Unrecoverable error", throwable);
                    return null;
                });*/

        /*celloSet.stream()
                .map(cello -> CompletableFuture.supplyAsync(() -> addAge(cello), executor)
//                        .get(1, TimeUnit.SECONDS)
//                        .acceptEither(timeoutAfter(1, TimeUnit.SECONDS),
//                                cello1 -> System.out.println("The price is: " + cello1))
//                                cello1 -> new Cello())
//                        .thenAccept(timeoutAfter(1L, TimeUnit.SECONDS))
                                .exceptionally(error -> {
                                    log.warn("Failed addAge to " + " : " + error);
                                    return new Cello();
//                            return null;
                                })
                )
                .collect(Collectors.toList());*/


//        CompletableFuture<Cello> timeout = failAfter(Duration.ofMillis(4000));

//        CompletableFuture<Cello> timeoutAfter = timeoutAfter(10, TimeUnit.SECONDS);

        List<CompletableFuture<Cello>> futures = celloSet.stream()
                .map(cello -> CompletableFuture.supplyAsync(() -> addAge(cello), executor)
//                                .applyToEither(timeout, Function.identity())
//                                .applyToEither(timeoutAfter, Function.identity())
                                .applyToEither(timeoutAfter(10, TimeUnit.SECONDS), Function.identity())
                                .exceptionally(error -> {
                                    log.warn("Failed addAge to " + cello.getName() + " : " + error);
//                                    return new CompletableFuture<Cello>();
                                    return null;
                                })

                )
                .collect(Collectors.toList());

        List<CompletableFuture<Cello>> futures2 = celloSet.stream()
                .map(cello -> CompletableFuture.supplyAsync(() -> addAge(cello), executor)
//                                .applyToEither(timeout, Function.identity())
//                                .applyToEither(timeoutAfter, Function.identity())
                                .applyToEither(timeoutAfter(10, TimeUnit.SECONDS), Function.identity())
                                .exceptionally(error -> {
                                    log.warn("Failed addAge to " + cello.getName() + " : " + error);
//                                    return new CompletableFuture<Cello>();
                                    return null;
                                })

                )
                .collect(Collectors.toList());

        System.out.println("1: " + futures.size());

       /* executor.shutdown();
        try {
            if (!executor.awaitTermination(12, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }*/

       /* List<CompletableFuture<Cello>> futures5 = new ArrayList<>();
        for (CompletableFuture<Cello> future : futures) {
            if (future.isDone()) {
                futures5.add(future);
            } else {
                future.cancel(true);
            }
        }*/

        List<Cello> cellos = futures.stream()
//                .filter(CompletableFuture::isDone)
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .filter(c -> c.getMaster() != null)
                .collect(Collectors.toList());




       /* try {
            executor.shutdown();
            executor.awaitTermination(0, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            log.error("Termination interrupted", e);

        } finally {
            executor.shutdownNow();
            if (!executor.isTerminated()) {
                log.warn("Killing non-finished tasks");
            }
        }*/

//        shutdownAndAwaitTermination(executor, 1);
        // Wait for all tasks to complete
        // Timeout is beyond reasonable doubt that completion should
        // have occurred unless there is an issue
//        CompletableFuture<Object> any = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[0]));
      /*  CompletableFuture<Void> any = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        List<Cello> cellos = new ArrayList<>();
        try {
            any.get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            cellos = futures.stream()
                    .map(CompletableFuture::join)
                    .filter(Objects::nonNull)
                    .filter(c -> c.getMaster() != null)
                    .collect(Collectors.toList());
            log.warn("Exception........");
        }
*/
//        executor.shutdown();
//        shutdownAndAwaitTermination(executor, 1);
        /*try {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            log.error("Termination interrupted", e);

        } finally {
            if (!executor.isTerminated()) {
                log.warn("Killing non-finished tasks");
            }
            executor.shutdownNow();
        }*/
     /*   try {
            executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        /*List<Cello> cellos = futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .filter(c -> c.getMaster() != null)
                .collect(Collectors.toList());*/

        System.out.println("2: " + cellos.size());

        cellos.forEach(System.out::println);

//        executor.shutdown();
        return cellos;
//        return new ArrayList<>();
      /*  Cello join = futures.stream()
                .min(Comparator.comparing(cf -> cf.join().getAge()))
                .get().join();*/
    }

    void shutdownAndAwaitTermination(ExecutorService threadPool, int timeoutInSecond) {
        threadPool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate

            if (!threadPool.awaitTermination(timeoutInSecond, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();                         // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!threadPool.awaitTermination(timeoutInSecond, TimeUnit.SECONDS))
                    log.error("Executor Thread Pool did not terminate");
            }
        } catch (InterruptedException ie) {

            log.error("Error in clean up of threads.", ie);
            // (Re-)Cancel if current thread also interrupted
            threadPool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    private Cello addAge(Cello cello) {
        sleepSeconds(cello.getSleep(), cello);

        Integer age = cello.getAge();
        if (cello.getAge() == 1) {
            log.warn("throw new RuntimeException();");
            throw new RuntimeException();
        }
        cello.setAge(age + 1);
        return cello;
    }

    private void sleepSeconds(long time, Cello cello) {
        try {
            log.info(cello.getName() + " sleep on " + cello.getSleep() + " sec.");
//            Thread.sleepSeconds(time);
            TimeUnit.SECONDS.sleep(time);
            log.info(cello.getName() + " awake");
        } catch (InterruptedException e) {
            log.warn(cello.getName() + " InterruptedException");
        }
    }

 /*   private Set<Cello> createCellos() {
        Set<Cello> cellos = new HashSet<>();

        Cello cello1 = new Cello();
        cello1.setName("First");
        cello1.setMaster("Stradivarius");
        cello1.setAge(200);

        Cello cello2 = new Cello();
        cello2.setName("Seconnd");
        cello2.setMaster("Guarnerius");
        cello2.setAge(197);

        Cello cello3 = new Cello();
        cello3.setName("Third");
        cello3.setMaster("Amati");

        cellos.add(cello1);
        cellos.add(cello2);
        cellos.add(cello3);

        return cellos;
    }

    private Set<Cello> createCellos2() {

        Set<Cello> cellos = new HashSet<>();

        Cello cello1 = new Cello();
        cello1.setName("New1");
        cello1.setMaster("Banderas");
        cello1.setAge(20);

        Cello cello2 = new Cello();
        cello2.setName("New2");
        cello2.setMaster("Arni");
        cello2.setAge(19);

        Cello cello3 = new Cello();
        cello3.setName("New3");
        cello3.setMaster("NoName");

        cellos.add(cello1);
        cellos.add(cello2);
        cellos.add(cello3);

        return cellos;
    }*/


    public List<Cello> getCellos4(List<Cello> celloSet) {

        Executor executor = Executors.newFixedThreadPool(4);
        CompletionService<List<Cello>> completionService = new ExecutorCompletionService<>(executor);

        List<Cello> cellos = new ArrayList<>();
        for (int i = 0; i < celloSet.size(); i++) {
            int finalI = i;
            completionService.submit(() -> {
                Cello cello = celloSet.get(finalI);
                Cello cello1 = addAge(cello);
                cellos.add(cello1);
                return cellos;
            });
        }

        int received = 0;
        boolean errors = false;
        List<Cello> result = new ArrayList<>();
        while (received < 4 && !errors) {
            Future<List<Cello>> resultFuture;
            try {
                resultFuture = completionService.take();
                result = resultFuture.get();
                received++;
                // do something with the result
            } catch (Exception e) {
                //log
                errors = true;
            }
        }

        return result;
    }
}
