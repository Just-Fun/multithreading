package ua.com.serzh.quiz31;

/**
 * Created by Serzh on 8/12/16.
 */
public class App {
//        public synchronized void f() {
    public static synchronized void f() {
//    public synchronized void f() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getId() + " - f");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

        public static synchronized void g() {
//    public synchronized void g() {
        int j = 5;
        while (j-- > 0) {
            System.out.println(Thread.currentThread().getId() + " - g");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        App app1 = new App();
        App app2 = new App();
        Thread thread1 = new Thread(() -> app1.f());
        Thread thread2 = new Thread(() -> app2.g());

        /*App app = new App();
        Thread thread1 = new Thread(() -> app.f());
        Thread thread2 = new Thread(() -> app.g());*/

        thread1.start();
        thread2.start();
    }
}
