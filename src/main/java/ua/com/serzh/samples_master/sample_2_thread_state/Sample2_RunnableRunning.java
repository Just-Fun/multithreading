package ua.com.serzh.samples_master.sample_2_thread_state;


import static ua.com.serzh.samples_master.ThreadUtils.someLogic;

public class Sample2_RunnableRunning {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.print("+");
                    someLogic(1_000_000);
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("");
                    someLogic(10_000_000);
                }
            }
        });

        thread1.start();
        thread2.start();
    }

}