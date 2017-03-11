package ua.com.serzh.mgtu.run;

/**
 * Created by Serzh on 12/7/16.
 */
public class SeriesRunExample extends Thread {

    private static int currentMax = 1;
    private int mainId;
//        private static final Object waitObject = null;
        private static final Object waitObject = new Object();;
//    private /*final*/ Object waitObject;


    public SeriesRunExample() {
    }

    public SeriesRunExample(int mainId/*, Object waitObject*/) {
     /*   if (waitObject != null) {
//             waitObject = new Object();
        }*/
        this.mainId = mainId;
    }

    public static void example() {
        Object waitObject = new Object();
        for (int i = 0; i < 10; i++) {
            Thread thread = new SeriesRunExample(i/*, waitObject*/);
            thread.start();
        }
    }

    @Override
    public void run() {
        try {
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Start run of thread: " + mainId);
            synchronized (waitObject) {
                while (mainId >= currentMax) {
                    waitObject.wait();
                }

                currentMax++;
                System.out.println("Hello from thread: " + mainId);
                waitObject.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
