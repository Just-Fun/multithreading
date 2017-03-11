package ua.com.serzh.samples_master.sample_3_thread_control;

import static ua.com.serzh.samples_master.ThreadUtils.print;

public class Sample2_StopThreadDeath {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                print("Before sleep");
                Thread.sleep(1500);
                print("After sleep");
            } catch (Throwable ex) {
                print("catch Throwable ex");
                ex.printStackTrace();
            }
        });

        /*Thread thread = new Thread() {
            public void run() {
                try {
                    print("Before sleep");
                    Thread.sleep(1500);
                    print("After sleep");
                } catch (Throwable ex) {
                    print("catch Throwable ex");
                    ex.printStackTrace();
                }
            }
        };*/
        thread.start();

        Thread.sleep(1000);
//        thread.stop();
    }

}
