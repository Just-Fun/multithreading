package ua.com.serzh.com.yield;

/**
 * Created by Serzh on 8/10/16.
 */
class AppThread extends Thread {

    public AppThread() {

    }

    public void run() {

        Thread ct = Thread.currentThread();
        System.out.println("Дочернийпоток - " + ct.getName());
        for (int i = 1; i <= 2; i++) {
            System.out.println("Значение цикла дочернего потока " + ct.getName() + " - " + i);
            System.out.println(ct.getName() + " передал управление");
            Thread.yield();
        }
        System.out.println("Работа дочернего потока завершена - " +
                ct.getName());
    }

    public static void main(String[] args) {
        System.out.println("Родительский поток.");
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new AppThread());
            t.start();
//            Thread.yield();
        }
    }
}
