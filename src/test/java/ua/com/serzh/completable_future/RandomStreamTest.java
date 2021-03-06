package ua.com.serzh.completable_future;

/**
 * Created by Serzh on 3/11/17.
 */
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @test
 * @run testng RandomStreamTest
 * @summary test stream methods on Random
 * @author Brian Goetz
 */
public class RandomStreamTest {

    private static final int SIZE = 1000;

    @DataProvider(name = "suppliers")
    public Object[][] randomSuppliers() {
        return new Object[][] {
                {new Random(), SIZE},
                {new SecureRandom(), SIZE}
        };
    }

    @Test(dataProvider = "suppliers")
    public void testRandomIntStream(final Random random, final int count) {
        final List<Integer> destination = new ArrayList<>(count);
        random.ints().limit(count).forEach(destination::add);
        assertEquals(destination.size(), count);
    }

    @Test(dataProvider = "suppliers")
    public void testRandomLongStream(final Random random, final int count) {
        final List<Long> destination = new ArrayList<>(count);
        random.longs().limit(count).forEach(destination::add);
        assertEquals(destination.size(), count);
    }

    @Test(dataProvider = "suppliers")
    public void testRandomDoubleStream(final Random random, final int count) {
        final List<Double> destination = new ArrayList<>(count);
        random.doubles().limit(count).forEach(destination::add);
        random.doubles().limit(count).forEach(d -> assertTrue(d >= 0.0 && d < 1.0));
        assertEquals(destination.size(), count);
    }

    @Test
    public void testIntStream() {
        final long seed = System.currentTimeMillis();
        final Random r1 = new Random(seed);
        final int[] a = new int[SIZE];
        for (int i=0; i < SIZE; i++) {
            a[i] = r1.nextInt();
        }

        final Random r2 = new Random(seed); // same seed
        final int[] b = r2.ints().limit(SIZE).toArray();
        assertEquals(a, b);
    }

    @Test
    public void testLongStream() {
        final long seed = System.currentTimeMillis();
        final Random r1 = new Random(seed);
        final long[] a = new long[SIZE];
        for (int i=0; i < SIZE; i++) {
            a[i] = r1.nextLong();
        }

        final Random r2 = new Random(seed); // same seed
        final long[] b = r2.longs().limit(SIZE).toArray();
        assertEquals(a, b);
    }

    @Test
    public void testDoubleStream() {
        final long seed = System.currentTimeMillis();
        final Random r1 = new Random(seed);
        final double[] a = new double[SIZE];
        for (int i=0; i < SIZE; i++) {
            a[i] = r1.nextDouble();
        }

        final Random r2 = new Random(seed); // same seed
        final double[] b = r2.doubles().limit(SIZE).toArray();
        assertEquals(a, b);
    }

    @Test
    public void testThreadLocalIntStream() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        testRandomResultSupplierConcurrently(() -> tlr.ints().limit(SIZE).boxed().collect(toList()));
    }

    @Test
    public void testThreadLocalLongStream() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        testRandomResultSupplierConcurrently(() -> tlr.longs().limit(SIZE).boxed().collect(toList()));
    }

    @Test
    public void testThreadLocalDoubleStream() throws InterruptedException, ExecutionException, TimeoutException {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        testRandomResultSupplierConcurrently(() -> tlr.doubles().limit(SIZE).boxed().collect(toList()));
    }

    <T> void testRandomResultSupplierConcurrently(Supplier<T> s) throws InterruptedException, ExecutionException, TimeoutException {
        // Produce 10 completable future tasks
        final int tasks = 10;
        List<CompletableFuture<T>> cfs = Stream.generate(() -> CompletableFuture.supplyAsync(s)).
                limit(tasks).collect(toList());

        // Wait for all tasks to complete
        // Timeout is beyond reasonable doubt that completion should
        // have occurred unless there is an issue
        CompletableFuture<Void> all = CompletableFuture.allOf(cfs.stream().toArray(CompletableFuture[]::new));
        all.get(1, TimeUnit.MINUTES);

        // Count the distinct results, which should equal the number of tasks
        long rc = cfs.stream().map(CompletableFuture::join).distinct().count();
        assertEquals(rc, tasks);
    }
}