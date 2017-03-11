package ua.com.serzh.tutorial;

/**
 * Created by serzh on 06.04.16.
 */
public class SynchronizedCounter {
    private int c = 0;
    private Object lock = new Object();

    public synchronized void increment() {
        c++;
    }

    public void decrement() {
        synchronized (lock) {
            c--;
        }
    }

    public synchronized int value() {
        return c;
    }
}
