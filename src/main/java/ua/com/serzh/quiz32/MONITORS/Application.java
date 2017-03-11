package ua.com.serzh.quiz32.MONITORS;

/**
 * Created by serzh on 18.04.16.
 */
public class Application {
    public static void main(String[] args) throws Exception {


        Object x = new Object();
        synchronized(x) {
            synchronized(x) {
                x.notify();
            }
        }

        /*Object x = new Object();
        Object y = new Object();
        synchronized(x) {
            synchronized(y) {
                x.notify();
                y.notify();
            }
        }
*/
        /*Object x = new Object();
        Object y = x;
        synchronized(x) {
            y.notify();
        }
*/
        // IllegalMonitorStateException
        /*Object x = new Object();
        Object y = new Object();
        synchronized(x) {
            y.notify();*/

        //выполнится и завершится
       /* Object ref = new Object();
        synchronized (ref) {
            ref.notifyAll();*/

        // IllegalMonitorStateException
        /*synchronized (new Object()) {
            new Object().notifyAll();
        }*/

    }
}
