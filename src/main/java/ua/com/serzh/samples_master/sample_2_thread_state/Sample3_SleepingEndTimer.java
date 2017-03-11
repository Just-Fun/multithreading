package ua.com.serzh.samples_master.sample_2_thread_state;

import static ua.com.serzh.samples_master.ThreadUtils.print;


public class Sample3_SleepingEndTimer {

    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        print("Before sleep");
                        long time = now();

                        Thread.sleep(1000);

                        print("After sleep: " + (now() - time) + "ms");
                    } catch (InterruptedException e) {
                        print("sleep interrupted");
                    }
                }
            }
        });

        thread.start();

        thread.interrupt();
    }

    private static long now() {
        return System.currentTimeMillis()/*Calendar.getInstance().getTimeInMillis()*/;
    }

}
