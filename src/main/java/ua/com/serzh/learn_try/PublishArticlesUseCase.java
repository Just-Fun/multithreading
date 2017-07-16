package ua.com.serzh.learn_try;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author sergii.zagryvyi on 15.07.2017
 */
public class PublishArticlesUseCase {


    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

   /*     List<String> strings = publishArticles(list);
        System.out.println(strings);*/

        ExecutorService executor = Executors.newSingleThreadExecutor();
//    AsyncResource asyncResource = new AsyncResource();
//    asyncResource.setExecutorService(executor);

        for (Integer integer : list) {
            CompletableFuture.runAsync(
                    () ->
                            fromInt(integer), executor)
//                    .thenRun(callAndVerify)
                    .get(1, TimeUnit.SECONDS);
        }

    }


    public static List<String> publishArticles(List<Integer> articles) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        return articles.stream()
                .map(num -> {
                    Future<String> task = executorService.submit(() -> fromInt(num));

                    try {
                        return task.get(1, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        return "error";
                    }
                })
                .collect(Collectors.toList());
    }

    private static String fromInt(Integer num) {
        System.out.println("fromInt, num: " + num);
        if (num == 2) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finish " + num);
        return Integer.toString(num);
    }
}
