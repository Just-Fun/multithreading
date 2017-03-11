package ua.com.serzh.quiz32;

/**
 * Created by serzh on 01.04.16.
 */
public class IntHolder {
    private volatile int value = 0;

    public int getValue() {
        return value;
    }

    public void inc() {
        value++;
    }

    public static void main(String[] args) {

        Thread thread0 = new Thread(new Runnable() {
            IntHolder holder1 = new IntHolder();

            @Override
            public void run() {
                while (true) {
                    holder1.inc();
                    System.out.println(Thread.currentThread().getId() + " Thread inc value" + " and now get int = " + holder1.getValue());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            IntHolder holder2 = new IntHolder();

            @Override
            public void run() {
                while (true) {
                    holder2.inc();
                    System.out.println(Thread.currentThread().getId() + " Thread inc value" + " and now get int = " + holder2.getValue());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread0.start();
        thread1.start();

    }
}
