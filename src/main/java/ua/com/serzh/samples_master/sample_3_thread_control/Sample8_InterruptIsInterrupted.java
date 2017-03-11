package ua.com.serzh.samples_master.sample_3_thread_control;


import static ua.com.serzh.samples_master.ThreadUtils.print;

public class Sample8_InterruptIsInterrupted {

    static class MyRunnable implements Runnable {

        private String message;

        public MyRunnable(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                print(message);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    print("interrupt() in exeption");
                    Thread.currentThread().interrupt();
                }
            }
            print("Exit");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new MyRunnable("Thread1"));
        Thread thread2 = new Thread(new MyRunnable("Thread2"));

        thread1.start();
        thread2.start();

        Thread.sleep(3000);

        /*if (thread1.isInterrupted()) {
        thread1.interrupt();
        }*/
//        thread1.interrupt();
        thread2.interrupt();

        if (thread2.isInterrupted()) {
            thread1.start();
        }
        print("Exit main");
    }

}
