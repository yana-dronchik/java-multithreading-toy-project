import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureExamples.executeWithSingleThreadExecutor();
        FutureExamples.executeWithFixedThreadPoolExecutor();
        FutureExamples.executeWithCompletableFuture_thenApplyAsync();
        FutureExamples.executeWithCompletableFuture_thenCombineAsync();
        FutureExamples.executeWithCompletableFuture_thenComposeAsync();
    }
}