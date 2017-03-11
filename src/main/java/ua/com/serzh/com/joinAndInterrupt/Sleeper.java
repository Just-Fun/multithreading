package ua.com.serzh.com.joinAndInterrupt;

/**
 * Created by Serzh on 8/10/16.
 */
class Sleeper extends Thread {

    private int duration;
    public Sleeper(String name, int sleepTime) {
        super(name);
        duration = sleepTime;
        start();
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " запустился и будет спать.");
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " прерван");
            return;
        }
        System.out.println(getName() + " активизировался.");
    }
}

class Joiner implements Runnable {
    private Sleeper sleeper;
    private Thread t;
    public Joiner(String name, Sleeper sleeper) {
        this.sleeper = sleeper;
        t = new Thread(this);
        t.setName(name);
        t.start();

    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " запустился и вызовет sleeper.joinAndInterrupt()");
            sleeper.join();
            System.out.println(t.getName() + " завершен.");
        } catch (InterruptedException e) {
            System.out.println(t.getName() + " прерван.");
        }
    }

    public Thread getThread() {

        return t;
    }

    public static void main(String[] args) {
        Sleeper sleepy1 = new Sleeper("Sleepy 1", 1500),
                sleepy2 = new Sleeper("Sleepy 2", 2000);
        Joiner joiner1 = new Joiner("Joiner 1", sleepy1),
                joiner2 = new Joiner("Joiner 2", sleepy2);
        sleepy1.interrupt();
        joiner2.getThread().interrupt();
    }
}
