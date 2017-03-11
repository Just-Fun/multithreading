package ua.com.serzh.samples_master.sample_1_run_thread;

import static ua.com.serzh.samples_master.ThreadUtils.*;
import static ua.com.serzh.samples_master.ThreadUtils.print;

/**
 * Created by serzh on 01.04.16.
 */
public class Sample0_MainMethod {

    public static void main(String[] args) throws InterruptedException {
        print("Начали!");

        Thread.currentThread().join();

        print("закончили!");
    }
}
