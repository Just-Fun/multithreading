package ua.com.serzh.quiz31;

/**
 * Created by Serzh on 8/12/16.
 */
public class Application {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello!");
            }
            public void start() {
                System.out.println("Hello!");
            }
        }).start();
    }

}
