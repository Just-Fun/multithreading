package ua.com.serzh;

/**
 * Created by Serzh on 8/12/16.
 */
public class Main3 {

    static synchronized void stat() { // static лочиться по классу
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " - Static method");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void nonStatic() { // nonStatic лочиться п экземпляру класса
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " - nonStatic method");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(() -> stat());
        Thread thread1 = new Thread(() -> stat());
//        Thread thread = new Thread(() -> new Main3().nonStatic());
//        Thread thread1 = new Thread(() -> new Main3().nonStatic());

        thread.start();
        thread1.start();
    }
}
