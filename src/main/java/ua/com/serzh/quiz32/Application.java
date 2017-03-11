package ua.com.serzh.quiz32;

/**
 * Created by serzh on 01.04.16.
 */
public class Application {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello1!" + Thread.currentThread().getName());
            }

            public void start() {
                System.out.println("Hello2!" + Thread.currentThread().getName());
            }
        }).start();
    }
}

//  + Thread.currentThread().getName()
