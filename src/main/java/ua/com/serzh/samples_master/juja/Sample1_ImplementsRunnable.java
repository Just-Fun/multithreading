package ua.com.serzh.samples_master.juja;

import static ua.com.serzh.samples_master.ThreadUtils.print;
import static ua.com.serzh.samples_master.ThreadUtils.sleepRandom;

public class Sample1_ImplementsRunnable {

    static class MyRunnable implements Runnable {

        private int counter;
        private String message;

        public MyRunnable(int counter, String message) {
            this.counter = counter;
            this.message = message;
        }

        @Override
        public void run() {
            while (counter-- > 0) {
                print(message);

                sleepRandom(1000);
            }
            System.out.println("Вышли из потока!" + Thread.currentThread().getId());
        }
    }

    public static void main(String[] args) {
        Runnable task1 = new MyRunnable(15,"Я круче!");
        Runnable task2 = new MyRunnable(3, "Нет Я!");
        Runnable task3 = new MyRunnable(3, "Нет Я!");

//        Thread thread1 = new Thread(task1);
        Thread thread1 = new Thread(task1);
        thread1.setDaemon(true);
        Thread thread2 = new Thread(task2);
        Thread thread3 = new Thread(task3);
        Thread thread = new Thread(new MyRunnable(4, "Ну-ну..."));

        thread1.start();
        thread2.start();
        thread3.start();
        thread.start();
        System.out.println("Вышли из main");

      /*  thread1.run();
        thread2.run();*/
    }
}
