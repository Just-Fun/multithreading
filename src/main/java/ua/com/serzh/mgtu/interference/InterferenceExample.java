package ua.com.serzh.mgtu.interference;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Serzh on 12/4/16.
 */
public class InterferenceExample {

    Logger logger = Logger.getLogger(InterferenceExample.class);
    private static final int HundredMillion = 100_000_000;
    private AtomicInteger counter = new AtomicInteger();

    public boolean stop() {
        return counter.incrementAndGet() > HundredMillion;
    }

    public void example() {
        InterferenceThread thread1 = new InterferenceThread(this);
        InterferenceThread thread2 = new InterferenceThread(this);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Exprxcted: " + HundredMillion);
        System.out.println("Result: " + thread1.getI());
    }
}
