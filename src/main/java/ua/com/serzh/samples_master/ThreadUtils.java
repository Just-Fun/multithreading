package ua.com.serzh.samples_master;

import java.util.Random;

/**
 * Created by serzh on 01.04.16.
 */
public class ThreadUtils {
    private static String printed;

    public static synchronized void print(String message) {
        long id = Thread.currentThread().getId();
        System.out.println(id + ": " + message);
        printed = message;
    }

    public static synchronized void printUniq(String message) {
        if (!message.equals(printed)) {
            print(message);
        }
    }

    public static void sleepRandom(int mills) {
        sleep(new Random().nextInt(mills));
    }

    public static void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void someLogic(int b) {
        double a = 13.0;
        for (int i = 0; i < b; i++) {
            a = Math.cos(Math.sqrt(a + Math.sin(a)));
        }
    }
}
