package ua.com.serzh.elizarov_roman;


//import org.openjdk.jcstress.annotations.*;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serzh on 1/9/17.
 */

//@JCStressTest
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit
public class SimpleTest1 {

    Map<String, String> map;

    @Setup
    public void prepare() {
        map = new HashMap<>();
        map.put("adfdv", "dsjv");
    }

    @GenerateMicroBenchmark
    public String testMethod() {
        return map.get("adfdv");
    }

}
