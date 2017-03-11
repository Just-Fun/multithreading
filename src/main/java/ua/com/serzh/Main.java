package ua.com.serzh;

/**
 * Created by serzh on 28.02.16.
 */
public class Main {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("1. Runnable < - > Running");

                try {
                    System.out.println("1. Before sleep");
//                        long time = Calendar.getInstance().getTimeInMillis();
                    long time = System.currentTimeMillis();

                    Thread.sleep(1000);

                    System.out.println("1. After sleep: " + (System.currentTimeMillis() - time) + " millisec");
                } catch (InterruptedException e) {
                    System.out.println("1. Interrupnuli");
                    e.printStackTrace();
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("2. Runnable < - > Running");

                    try {
                        System.out.println("2. Before sleep");
//                        long time = Calendar.getInstance().getTimeInMillis();
                        long time = System.currentTimeMillis();

                        Thread.sleep(1000);

                        System.out.println("2. After sleep: " + (System.currentTimeMillis() - time) + " millisec");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
        thread1.start();
        thread.interrupt();
    }
}
