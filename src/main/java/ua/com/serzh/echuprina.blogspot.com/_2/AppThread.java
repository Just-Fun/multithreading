package ua.com.serzh.echuprina.blogspot.com._2;

/**
 * Created by Serzh on 8/10/16.
 */
class AppThread extends Thread {

    public AppThread() {

    }

    public void run() {

        System.out.println("Дочерний поток.");
        for(int i = 1; i <= 5; i++) {
            System.out.println("Значение цикла дочернего потока - " + i);
        }
        System.out.println("Работа дочернего потока завершена.");
    }

    public static void main(String[] args) {
        System.out.println("Родительский поток.");
        Thread t = new Thread(new AppThread());
        t.start();
    }
}



