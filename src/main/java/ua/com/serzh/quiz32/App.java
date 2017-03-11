package ua.com.serzh.quiz32;

/**
 * Created by serzh on 4/18/16.
 */
public class App {
    public static synchronized void f() {
        while (true) {
            System.out.println(Thread.currentThread().getId() + ": f...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void g() {
        while (true) {
            System.out.println(Thread.currentThread().getId() + ": g...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                App app = new App();
                System.out.println(Thread.currentThread().getId() + " ? 1 before f");
                app.f();
                System.out.println(Thread.currentThread().getId() + " ? 1 before g");
                app.g();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                App app = new App();
                System.out.println(Thread.currentThread().getId() + " ? 2 before f");
                app.f();
                System.out.println(Thread.currentThread().getId() + " ? 2 before g");
                app.g();
            }
        });
        thread1.start();
        thread2.start();
    }
}
