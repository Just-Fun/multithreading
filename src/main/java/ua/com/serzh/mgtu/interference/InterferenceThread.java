package ua.com.serzh.mgtu.interference;

/**
 * Created by Serzh on 12/4/16.
 */
public final class InterferenceThread extends Thread{
//    Logger logger = Logger.getLogger(InterferenceThread.class);

    private final InterferenceExample checker;
    private static StateObject stateObject= new StateObject();
    //    private static int i;
    private static volatile int i;
//    private static Object lock = new Object();

    public InterferenceThread(InterferenceExample checker) {
        this.checker = checker;
    }

    @Override
    public void run() {
        while (!checker.stop()) {
            // do something
//            increment();
            stateObject.increment();

        }
//        logger.info("Finished: " + Thread.currentThread().getName());
        System.out.println("Finished: " + Thread.currentThread().getName());
    }

//    private void increment() {
//    private synchronized void increment() {
//    private static synchronized void increment() {
    private void increment() {
//        synchronized (lock) {
        synchronized (checker) {
            i++;
        }
    }

    public int getI() {
//        return i;
        return stateObject.getI();
    }
}
