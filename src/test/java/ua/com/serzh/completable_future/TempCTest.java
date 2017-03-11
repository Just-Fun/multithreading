package ua.com.serzh.completable_future;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Serzh on 3/11/17.
 */
public class TempCTest {


    @Test
    public void getCellosTest() throws Exception {
        List<Cello> cellosList = createCellos();

        PlayWithCF play = new PlayWithCF();
        List<Cello> cellos = play.getCellos(cellosList);

        assertEquals(2, cellos.size());
    }

    @Test
    public void getCellosTestThrowRuntimeException() throws Exception {
        List<Cello> cellosList = createCellos();
        List<Cello> cellosList2 = createCellos2();
        Cello cello = new Cello();
        cello.setName("OneYearCello");
        cello.setAge(1);
        cellosList.add(cello);
        cellosList.addAll(cellosList2);

        PlayWithCF play = new PlayWithCF();
        List<Cello> cellos = play.getCellos(cellosList);

        assertEquals(2, cellos.size());
    }



    private List<Cello> createCellos() {
        List<Cello> cellos = new ArrayList<>();

        Cello cello1 = new Cello();
        cello1.setName("First");
        cello1.setMaster("Stradivarius");
        cello1.setAge(200);
        cello1.setSleep(1);

        Cello cello2 = new Cello();
        cello2.setName("Seconnd");
        cello2.setMaster("Guarnerius");
        cello2.setAge(197);
        cello2.setSleep(1);

        Cello cello3 = new Cello();
        cello3.setName("Third");
//        cello3.setMaster("Amati");
        cello3.setAge(100);
        cello3.setSleep(1);

        cellos.add(cello1);
        cellos.add(cello2);
        cellos.add(cello3);

        return cellos;
    }

    private List<Cello> createCellos2() {

        List<Cello> cellos = new ArrayList<>();

        Cello cello1 = new Cello();
        cello1.setName("New1");
        cello1.setMaster("Banderas");
        cello1.setAge(20);
        cello1.setSleep(3);

        Cello cello2 = new Cello();
        cello2.setName("New2");
        cello2.setMaster("Arni");
        cello2.setAge(19);
        cello2.setSleep(1);

        Cello cello3 = new Cello();
        cello3.setName("New3");
        cello3.setMaster("NoName");
        cello3.setSleep(1);

        Cello cello4 = new Cello();
        cello4.setName("New4");
        cello4.setMaster("Arni4");
        cello4.setAge(21);
        cello4.setSleep(1);

        Cello cello5 = new Cello();
        cello5.setName("New5");
        cello5.setMaster("Arni5");
        cello5.setAge(22);
        cello5.setSleep(4);

        Cello cello6 = new Cello();
        cello6.setName("New6");
        cello6.setMaster("Arni6");
        cello6.setAge(26);
        cello6.setSleep(1);

        cellos.add(cello1);
        cellos.add(cello2);
        cellos.add(cello3);
        cellos.add(cello4);
        cellos.add(cello5);
        cellos.add(cello6);

        return cellos;
    }


    @Test
    public  <T> void testRandomResultSupplierConcurrently() throws InterruptedException, ExecutionException, TimeoutException {


        // Wait for all tasks to complete
        // Timeout is beyond reasonable doubt that completion should
        // have occurred unless there is an issue
    /*    CompletableFuture<Void> all = CompletableFuture.allOf(cfs.toArray(new CompletableFuture[0]));
        all.get(1, TimeUnit.MINUTES);

        // Count the distinct results, which should equal the number of tasks
        long rc = cfs.stream().map(CompletableFuture::join).distinct().count();
        assertEquals(rc, tasks);*/
    }

}