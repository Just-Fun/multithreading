package ua.com.serzh.samples_master.sample_3_thread_control;


import static ua.com.serzh.samples_master.ThreadUtils.print;
import static ua.com.serzh.samples_master.ThreadUtils.sleep;

public class Sample5_Join {

    static class MyRunnable implements Runnable {

        private String name;
        private int count;

        public MyRunnable(String name, int count) {
            this.name = name;
            this.count = count;
        }

        @Override
        public void run() {
            while (count-- > 0) {
                print(name);
                sleep(1000);
            }
            print(name + " finished.");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(new MyRunnable("thread1", 2));
        Thread thread2 = new Thread(new MyRunnable("thread2", 2));
        Thread thread3 = new Thread(new MyRunnable("thread3", 2));

//        thread1.setDaemon(true);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
        print("main waited till all .join() metod finished");
    }

}
