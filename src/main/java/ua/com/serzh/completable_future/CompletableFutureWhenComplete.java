package ua.com.serzh.completable_future;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Sergey on 6/13/17.
 */
public class CompletableFutureWhenComplete {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        CompletableFutureWhenComplete whenComplete = new CompletableFutureWhenComplete();
        whenComplete.doWork(new String[]{"1", "2", "ups", "4"});
    }

    private void doWork(String[] array) {
        List<Integer> collect = Arrays.stream(array)
                .map(number -> CompletableFuture.supplyAsync(() -> getInt(number), executorService)
                                .whenComplete((result, exception) -> {
                                    if (Objects.nonNull(exception)) {
//                                      do some with exception
                                        System.out.println("Blabla: " + exception);
                                    } else {
//                                        do some with value
                                        System.out.println("Result: " + result);
                                    }
//                                    log.info()
                                })
                )
                .collect(Collectors.toList())
                .stream().map(CompletableFuture::join).collect(Collectors.toList());

        System.out.println(collect);

    }

    private int getInt(String s) {
        return Integer.parseInt(s);
    }
}

/*.supCompletableFutureplyAsync(() -> {
   //set shared request data in nested thread
   SharedRequestDataHolder.set(sharedRequestData);
   UnitAvailabilityCheckResult uaCheckResult = determineUnitAvailabilityResult(reservationResult);
   SharedRequestDataHolder.get(ReservationResultsSharedRequestData.class).setUnitAvailabilityCheckResult(uaCheckResult);
   return uaCheckResult;
}, executorService)
   .whenComplete((uaCheckResult, exception) -> {

       if (Objects.nonNull(exception)) {
           uaCheckResult = UnitAvailabilityCheckResult.error(exception.getMessage());
           SharedRequestDataHolder.get(ReservationResultsSharedRequestData.class)
               .setUnitAvailabilityCheckResult(uaCheckResult);
       }

       updateUaState(reservationResultId, uaCheckResult);

       log.info(LogUtils.prepareMessage(StringUtils.EMPTY, "UA_VALIDATION", true));

       SharedRequestDataHolder.clearSharedData();

   });
*/
