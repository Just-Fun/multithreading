package ua.com.serzh.com._1;

/**
 * Created by Serzh on 8/10/16.
 */
public class OneThread {
    public static void main(String args[]) {
        OneThread ot = new OneThread();
    }

    public OneThread() {

        System.out.println("Запускаемсчетчик.");

        Runnable r = new NewThread();
        Thread t = new Thread(r);
        t.start();

        System.out.println("Пока выполняется циклсчетчика – Выведем это сообщение.");
                System.out.println("Ну и наверно посчитаем значение Pi в квадрате: " +
                        Math.PI * Math.PI);
    }
}
