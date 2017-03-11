package ua.com.serzh.echuprina.blogspot.com.daemon;

import java.util.concurrent.TimeUnit;

/**
 * Created by Serzh on 8/10/16.
 */
/*Основным назначением потока-демона является выполнение какой-то работы (например, таймер, проверка входящих сообщений)
 в фоновом режиме во время выполнения программы. При этом данный поток не является неотъемлемой частью программы.
 А это значит, что в случае завершения работы всех потоков, не являющихся демонами программа, завершает свою работу,
 не дожидаясь завершения работы потоков-демонов.*/

class Daemon implements Runnable {

    public void run() {
        try {
            Thread ct = Thread.currentThread();
            while(true){
                System.out.println("Запускаем поток-демон: " +
                        ct.getName());
                TimeUnit.MICROSECONDS.sleep(1);
            }            }
        catch(InterruptedException e) {
            System.out.println("Прерывание потока.");
        }
        finally {
            System.out.println("Сюда, поток-демон, никогда не зайдет.");
        }
    }

    public static void main(String[] args) {
        Runnable r = new Daemon();
        Thread t = new Thread(r, "daemon");
        t.setDaemon(true); // определяем поток, как демон
        t.start();

        for(int i = 1; i <= 20; i++) {
            System.out.println("цикл - " + i);
        }

        System.out.println("Завершение программы.");
    }
}
