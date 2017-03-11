package ua.com.serzh.samples_master.sample_2_thread_state;


import static ua.com.serzh.samples_master.ThreadUtils.*;

public class Sample9_SpuriousWakeup_Object {

    static Object monitor = new Object();
    static int itemsReady;

    public static void main(String[] args) throws Exception {
        Runnable consumer1 = new Runnable() {
            @Override
            public void run() {
                synchronized (monitor) {
                    try {
                        if (itemsReady <= 0) {      // usually this is "while"
                            print("One: Waiting...");
                            monitor.wait();
                            if (itemsReady <= 0)
                                print("One: Spurious Wakeup! Condition NOT true!");
                            else {
                                print("One: Wakeup! Let's work!");
                                --itemsReady;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Runnable consumer2 = new Runnable() {
            @Override
            public void run() {
                synchronized (monitor) {
                    if (itemsReady <= 0) {
                        print("Two: Got lock, but no work!");
                    } else {
                        print("Two: Got lock and immediatly start working!");
                        --itemsReady;
                    }
                }
            }
        };

        // let consumer 2 hit the lock
        new Thread(consumer1).start();
        sleep(500);

        // let consumer 1 enter condition wait
        new Thread(consumer1).start();
        sleep(500);

        new Thread(consumer2).start();
        sleep(500);

        synchronized (monitor) {
            // make condition true and signal one (!) consumer
            print("Producer: fill queue");
            itemsReady = 1;
            monitor.notify();
            monitor.notifyAll();
            sleep(500);
            print("Producer: released lock");
        }

        sleep(1000);
    }
}
