package ua.com.serzh.samples_master.sample_1_run_thread;

import ua.com.serzh.samples_master.ThreadUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Sample5_ToUseFutureTask {

    static class MyCallable implements Callable {
        @Override
        public Object call() {
            while (true) {
                ThreadUtils.print("Я тут!");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
            return null;
        }
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                ThreadUtils.print("А я тут!");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FutureTask task1 = new FutureTask(new MyCallable());
        FutureTask task2 = new FutureTask(new MyRunnable(), null);

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        Thread.sleep(3000);

        task1.cancel(true);
        task2.cancel(true);

    }

}
