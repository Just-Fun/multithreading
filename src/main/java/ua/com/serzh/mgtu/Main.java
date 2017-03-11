package ua.com.serzh.mgtu;

import ua.com.serzh.mgtu.run.SeriesRunExample;

/**
 * Created by Serzh on 12/4/16.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        long begin = System.currentTimeMillis();
//        RundomRunExample.example();
        SeriesRunExample.example();
//        new InterferenceExample().example();

        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - begin));
    }
}
