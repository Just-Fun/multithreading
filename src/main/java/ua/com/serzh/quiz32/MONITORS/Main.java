package ua.com.serzh.quiz32.MONITORS;

/**
 * Created by serzh on 4/19/16.
 */
public class Main {

    public static synchronized void f() {    }
    public static synchronized void g(Main arg) {
        synchronized (arg.getClass()) {

        }
    }
    public static void main(String[] args) {
        Main mainI = new MainImpl();
        Main main = new Main();
        System.out.println(mainI.getClass());
        System.out.println(main.getClass());
        System.out.println(Main.class);
        g(mainI);
    }
}
