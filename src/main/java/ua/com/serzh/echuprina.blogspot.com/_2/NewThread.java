package ua.com.serzh.echuprina.blogspot.com._2;

/**
 * Created by Serzh on 8/10/16.
 */
class NewThread implements Runnable {

    public NewThread() {

    }

    public void run() {

        System.out.println("Тело метода run(), созданного потока.");

    }

    public static void main(String args[]) {
        System.out.println("Основной поток.");
        Runnable r = new NewThread();
        Thread t = new Thread(r);
        t.start();
    }
}
