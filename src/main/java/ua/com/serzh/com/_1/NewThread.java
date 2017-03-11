package ua.com.serzh.com._1;

/**
 * Created by Serzh on 8/10/16.
 */
public class NewThread  implements Runnable{

    public NewThread() {

    }

    public void run() {

        long num = 0;

        while(num < 999999999) {
            num++;
        }

        System.out.println("Результат работы счетчика: " + num);

    }
}
