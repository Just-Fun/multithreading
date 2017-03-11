package ua.com.serzh.mgtu.stopExample;

/**
 * Created by Serzh on 12/4/16.
 */
public class TestExample {
    //    private boolean canWork = true;
    private volatile boolean canWork = true;

    public void doWork() {
        if (canWork) {
            // do sometghing
            System.out.println("doWork - " + Thread.currentThread().getName() + "");
        }
    }

    public void stop() {
        canWork = false;
        System.out.println("stop!!!!!");
    }

   /* private boolean canWork = true;

    public synchronized void doWork() {
        if (canWork) {
            // do sometghing
        }
    }

    public synchronized void stop() {
        canWork = false;
    }
*/
}
