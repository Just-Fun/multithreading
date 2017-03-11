package ua.com.serzh.presentations.multithreading;

/**
 * Created with IntelliJ IDEA.
 * User: viktor
 * Date: 11/17/14
 * Time: 10:07 AM
 */
public class ThreadDemo {
    public static void main(String[] args) {
        runThreadMethod1();
        runThreadMethod2();
    }

    private static void runThreadMethod2() {
        Thread th = new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 300 ; i++) {
                    System.out.println("Thread with thread inheritance started " + i);
                }
            }
        };
        th.start();
    }

    private static void runThreadMethod1() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 300; i++) {
                    System.out.println("Thread with Runnable started " + i);
                }
            }
        });
        th.start();
    }

}
