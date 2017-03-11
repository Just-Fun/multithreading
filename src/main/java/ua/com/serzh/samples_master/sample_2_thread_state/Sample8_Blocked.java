package ua.com.serzh.samples_master.sample_2_thread_state;

import java.io.*;

import static ua.com.serzh.samples_master.ThreadUtils.*;

public class Sample8_Blocked {

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                print("Blocked");

                try (OutputStream os = new FileOutputStream(new File("1.txt"))) {
                    os.write(new byte[1000000000]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                print("Running");
            }
        }
    }

    public static void main(String[] args) {

        final Thread main = new Thread(new MyRunnable());

        Thread interrupter = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    print("Try to interrupt...");

                    main.interrupt();

                    sleep(100);
                }
            }
        });

        main.start();
        interrupter.start();
    }

}
