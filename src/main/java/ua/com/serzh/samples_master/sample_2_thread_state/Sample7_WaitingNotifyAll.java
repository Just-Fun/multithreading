package ua.com.serzh.samples_master.sample_2_thread_state;

import static ua.com.serzh.samples_master.ThreadUtils.*;

public class Sample7_WaitingNotifyAll {

    static Object monitor = new Object();
    static boolean ready = false;

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            print("Before while");
            while (true) {
                synchronized (monitor) {
                    try {
                        while (!ready) {
                            print("Waiting... ");
                            monitor.wait();
                            print("Wakeup");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                print("Do my work");
                sleep(2000);
            }
        }
    }

    public static void main(String[] args) {
        Thread main1 = new Thread(new MyRunnable());
        Thread main2 = new Thread(new MyRunnable());
        Thread main3 = new Thread(new MyRunnable());

        Thread notifier = new Thread(() -> {
            while (true) {
                synchronized (monitor) {
                    ready = true;
                    print("Ready = true. Try to notify all...");
                    monitor.notifyAll();
                    print("After notify ");
                }
                sleep(2000);
            }
        });

        Thread releaser = new Thread(() -> {
            while (true) {
                synchronized (monitor) {
                    ready = false;
                    print("Ready = false");
                }
                sleep(2000);
            }
        });

        main1.start();
        main2.start();
        main3.start();
        notifier.start();
        releaser.start();
    }

}
