package ua.com.serzh.samples_master.sample_4_synchronized;

import ua.com.serzh.samples_master.ThreadUtils;

public class Sample4_DeadLock_SynchronizedWithPrimitive {

//        private static String monitor = "12";
    private static Integer monitor = 12;
//    private static Boolean monitor = true;

    private static int count = 0; // shared state

    static class MyRunnable implements Runnable {
        public void run() {
            int c = 5;
            while (--c >= 0) {
                synchronized (monitor) {
                    ThreadUtils.print("зашли: " + count);

                    int y = count;

                    ThreadUtils.print("прочитали: " + y);

                    count = y + 1;

                    ThreadUtils.print("просуммировали: " + count);
                }

                ThreadUtils.sleep(10);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyRunnable target = new MyRunnable();
        Thread thread1 = new Thread(target);
        Thread thread2 = new Thread(target);

        new Thread(() -> {
//                synchronized ("12") {
            synchronized (Integer.valueOf(12)) { // захватываем монитор примитива
//                synchronized (Boolean.TRUE) {
                try {
                    Thread.currentThread().join(); // и зависаем
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        ThreadUtils.print("итого: " + count);
    }

}
