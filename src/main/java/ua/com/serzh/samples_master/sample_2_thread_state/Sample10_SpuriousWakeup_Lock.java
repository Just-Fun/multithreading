package ua.com.serzh.samples_master.sample_2_thread_state;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ua.com.serzh.samples_master.ThreadUtils.*;


// TODO исследовать этот кейс
// thanks http://stackoverflow.com/a/7811704
public class Sample10_SpuriousWakeup_Lock {

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    static int itemsReady;

    public static void main(String[] args) throws Exception {
        Runnable consumer1 = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    if (itemsReady <= 0) {      // usually this is "while"
                        print("One: Waiting...");
                        condition.await();
                        if (itemsReady <= 0)
                            print("One: Spurious Wakeup! Condition NOT true!");
                        else {
                            print("One: Wakeup! Let's work!");
                            --itemsReady;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };

        Runnable consumer2 = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    if (itemsReady <= 0)
                        print("Two: Got lock, but no work!");
                    else {
                        print("Two: Got lock and immediatly start working!");
                        --itemsReady;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };


        // let consumer 1 enter condition wait
        new Thread(consumer1).start();
        sleep(500);

        lock.lock();
        try {
            // let consumer 2 hit the lock
            new Thread(consumer2).start();
            sleep(500);

            // make condition true and signal one (!) consumer
            print("Producer: fill queue");
            itemsReady = 1;
            condition.signal();
            sleep(500);
        } finally {
            // release lock
            lock.unlock();
        }

        print("Producer: released lock");
        sleep(500);
    }
}
