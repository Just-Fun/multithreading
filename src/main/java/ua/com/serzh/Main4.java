package ua.com.serzh;


/**
 * Created by Serzh on 8/12/16.
 */
public class Main4 {

    static Object monitor = new Object();
    static Object monitor2 = new Object();

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            synchronized (monitor) {
                int i = 5;
                while (i-- > 0) {
                    System.out.println(Thread.currentThread().getName() + " - start");
                    System.out.println("'Inside' thread удерживает monitor: " + Thread.holdsLock(monitor));
                    try {
                        monitor.wait();
                        System.out.println("Wakeup");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread notifier = new Thread(() -> {
            int i = 5;
            while (i-- > 0) {
                synchronized (monitor) {
//                synchronized (monitor2) { // IllegalMonitorStateException
                    System.out.println("Try to notify");
                    monitor.notifyAll();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("main удерживает monitor: " + Thread.holdsLock(monitor));
        System.out.println("thread удерживает monitor: " + thread.holdsLock(monitor));
        thread.start();
        notifier.start();
    }
}
