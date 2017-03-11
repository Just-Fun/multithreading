package ua.com.serzh.samples_master.sample_2_thread_state;

import static ua.com.serzh.samples_master.ThreadUtils.*;

/**
 * Created by serzh on 01.04.16.
 */
public class Sample1_NewDead {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                int count = 10;
                while (--count > 0) {
                    print("Running<->Runnable");

                    someLogic(1000000);
                }
                print("Almost Dead");
            }
        });

        print("New");

        thread.start();

        print("Runnable");

        thread.join();

        print("Dead");
    }
}
