package ua.com.serzh.samples_master.sample_4_synchronized;


import static ua.com.serzh.samples_master.ThreadUtils.print;

public class Sample1_DataRace {

    //    private static Object monitor = new Object();
    private static int count = 0; // shared state

    static class MyRunnable implements Runnable {
        public void run() {
//            synchronized (monitor) {
            print("Enter: " + count);

            int y = count;

            print("Read: " + y);

            count = y + 1;

            print("Sum: " + count);
//            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnable target = new MyRunnable();
        Thread thread1 = new Thread(target);
        Thread thread2 = new Thread(target);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        print("Total: " + count);
    }

}
