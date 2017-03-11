package ua.com.serzh.mgtu.interference;

/**
 * Created by Serzh on 12/4/16.
 */
public class StateObject {
    private int i;

    public synchronized void increment() {
            i++;
        }

    public int getI() {
        return i;
    }
}
