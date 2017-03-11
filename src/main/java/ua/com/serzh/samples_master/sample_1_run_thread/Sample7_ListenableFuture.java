package ua.com.serzh.samples_master.sample_1_run_thread;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static ua.com.serzh.samples_master.ThreadUtils.*;


// Рекомендую использовать
// https://code.google.com/p/guava-libraries/wiki/ListenableFutureExplained
// Этот класс написан в демо целях
public class Sample7_ListenableFuture {

    static class MyCallable implements Callable {

        @Override
        public Object call() {
            int count = new Random().nextInt(10);
            while (--count > 0) {
                print("Я тут!");

                sleep(500);
            }
            return Thread.currentThread().getId();
        }
    }

    public static interface ListenerService extends ExecutorService {
        void onComplete(Completable completable);
    }

    public static class ExecutorListenerService implements ListenerService {

        private ExecutorService executor;
        private Completable completable;

        public ExecutorListenerService(ExecutorService executor) {
            this.executor = executor;
        }

        @Override
        public void shutdown() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Runnable> shutdownNow() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isShutdown() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isTerminated() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> Future<T> submit(Runnable task, T result) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Future<?> submit(Runnable task) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void execute(Runnable command) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            final Future<T> future = executor.submit(task);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (completable == null) {
                        throw new IllegalArgumentException("Please call onComplete(Completable)");
                    }
                    try {
                        completable.complete(future.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            return future;
        }

        @Override
        public void onComplete(Completable completable) {
            this.completable = completable;
        }
    }

    public static interface Completable {
        void complete(Object object);
    }

    public static void main(String[] args) throws Exception {
        Callable task1 = new MyCallable();
        Callable task2 = new MyCallable();
        Callable task3 = new MyCallable();

        final ExecutorService executor = Executors.newCachedThreadPool();
        ListenerService listener = new ExecutorListenerService(executor);

        final int[] remainingFutures = {3};
        listener.onComplete(new Completable() {
            @Override
            public void complete(Object object) {
                print("Finished: " + object);

                remainingFutures[0]--;
                if (remainingFutures[0] == 0) {
                    executor.shutdown();
                }
            }
        });

        listener.submit(task1);
        listener.submit(task2);
        listener.submit(task3);
    }

}
