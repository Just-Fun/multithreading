package ua.com.serzh.mgtu;

/**
 * Created by Serzh on 12/4/16.
 */
public class RundomRunExample extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    public static void example() {
        for (int i = 0; i < 10; i++) {
            Thread thread = new RundomRunExample();
            thread.start();
        }
    }
}
