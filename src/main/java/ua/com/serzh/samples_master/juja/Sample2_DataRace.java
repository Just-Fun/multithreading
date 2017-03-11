package ua.com.serzh.samples_master.juja;


import static ua.com.serzh.samples_master.ThreadUtils.print;

public class Sample2_DataRace {

//    private static int count = 0; // shared state

    static class MyRunnable implements Runnable {
        private int count = 0;

        public int getCount() {
            return count;
        }

        private static final Object monitor = new Object();

        public /*synchronized*/ void run() {
//            synchronized (monitor) {
            print("Enter: " + count);

            int y = count;
             /*   Thread thread = Thread.currentThread();
                System.out.println("id: " + thread.getId());
                System.out.println("name: " + thread.getName());
                System.out.println("stacktrace: " + arrays.toString(thread.getStackTrace()));*/

            print("Read: " + y);

//                count = y + 1;
//                count++;
            print("Sum: " + count);
//            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnable target1 = new MyRunnable();
        MyRunnable target2 = new MyRunnable();
        MyRunnable target3 = new MyRunnable();
        Thread thread1 = new Thread(target1);
        Thread thread2 = new Thread(target2);
        Thread thread3 = new Thread(target3);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        int f = target1.getCount();
        int s = target1.getCount();
        int t = target1.getCount();

        int i = f + s + t;
        print("Total: " + i);
    }

}
