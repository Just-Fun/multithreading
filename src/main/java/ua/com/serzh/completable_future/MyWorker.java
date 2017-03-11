package ua.com.serzh.completable_future;

/**
 * Created by Serzh on 3/11/17.
 */
import org.apache.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class MyWorker implements Runnable {
    private  Logger log = Logger.getLogger(PlayWithCF2.class);

    private final int sleepTime;

    public MyWorker(final int sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        final long startTime = System.nanoTime();
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(sleepTime));
            log.info("Finished");
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            final long interruptedAfter = System.nanoTime() - startTime;
            log.warn(String.format("Interrupted after %,d nano seconds", interruptedAfter));
        }
    }
}
