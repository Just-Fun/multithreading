package ua.com.serzh.samples_master.sample_3_thread_control;

import static ua.com.serzh.samples_master.ThreadUtils.sleep;

/**
 * Created by serzh on 16.04.16.
 */
public class ThreadGroupDemo implements Runnable {
    public static void main(String[] args) {
        ThreadGroupDemo tg = new ThreadGroupDemo();
        tg.func();
    }

    public void func() {
        try {
            // create a parent ThreadGroup
            ThreadGroup pGroup = new ThreadGroup("Parent ThreadGroup");

            // create a child ThreadGroup for parent ThreadGroup
            ThreadGroup cGroup = new ThreadGroup(pGroup, "Child ThreadGroup");

            // create a thread
            Thread t1 = new Thread(pGroup, this);
            System.out.println("Starting " + t1.getName() + "...");
            t1.start();

            // create another thread
            Thread t2 = new Thread(cGroup, this);
            System.out.println("Starting " + t2.getName() + "...");
            t2.start();

            // display the number of active threads
            System.out.println("Active threads in \"" + pGroup.getName()
                    + "\" = " + pGroup.activeCount());

            // block until the other threads finish
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            System.out.println(ex.toString());
        }
    }

    // implements run()
    public void run() {

        for (int i = 0; i < 1000; i++) {
            i++;
        }
        sleep(1000);
        System.out.println(Thread.currentThread().getName() +
                " finished executing.");
    }

}
