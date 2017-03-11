package ua.com.serzh.samples_master.sample_3_thread_control;


import static ua.com.serzh.samples_master.ThreadUtils.print;

public class Sample4_GetCurrentThread {

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            print(thread.getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyRunnable());
        Thread thread2 = new Thread(new MyRunnable());

        Thread main = Thread.currentThread();
        print(main.getName());
        thread1.start();
        thread2.start();
    }

}
