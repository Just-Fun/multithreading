package ua.com.serzh.samples_master.sample_2_thread_state;

import java.util.Calendar;

import static ua.com.serzh.samples_master.ThreadUtils.*;

public class Sample4_SleepingInterrupt {

    public static void main(String[] args) {
        final Thread thread = new Thread(() -> {
            while (true) {
                print("Sleeping");
                long time = now();
                try {

                    Thread.sleep(10000);

                } catch (InterruptedException e) {
                    print("Sleep interrupted: " + (now() - time) + "ms");
                }
                print("Running");
            }
        });

        Thread interrupter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    print("Try to interrupt..");

                    thread.interrupt();

                    sleep(1000);
                }
            }
        });

        thread.start();
        interrupter.start();
    }

    private static long now() {
        return Calendar.getInstance().getTimeInMillis();
    }

}
