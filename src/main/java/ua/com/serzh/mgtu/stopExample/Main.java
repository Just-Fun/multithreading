package ua.com.serzh.mgtu.stopExample;

/**
 * Created by Serzh on 12/4/16.
 */
public class Main {
//    TestExample example;

    public static void main(String[] args) throws InterruptedException {
        TestExample example = new TestExample();
        Test test1 = new Test(example);
        Test test2 = new Test(example);
        Test test3 = new Test(example);

        test1.start();
        test2.start();
        test3.start();
        Thread.sleep(1);
        example.stop();
    }

    static class Test extends Thread {
        TestExample example;

        public Test(TestExample example) {
            this.example = example;
        }

        @Override
        public void run() {
            while (true) {
                example.doWork();
            }
        }
    }
}
