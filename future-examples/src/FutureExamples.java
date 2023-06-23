import java.util.concurrent.*;

public final class FutureExamples {
    private FutureExamples() {
    }

    public static void executeWithSingleThreadExecutor() throws ExecutionException, InterruptedException {
        System.out.println("--- executeWithSingleThreadExecutor");
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            Future<?> future1 = executorService.submit(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println("Likely to be printed first");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Future<?> future2 = executorService.submit(() -> System.out.println("Likely to be printed second"));
            future1.get();
            future2.get();
        }
        System.out.println();
    }

    public static void executeWithFixedThreadPoolExecutor() throws ExecutionException, InterruptedException {
        System.out.println("--- executeWithFixedThreadPoolExecutor");
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            Future<?> future1 = executorService.submit(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println("Likely to be printed second");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            Future<?> future2 = executorService.submit(() -> System.out.println("Likely to be printed first"));
            future1.get();
            future2.get();
        }
        System.out.println();
    }

    public static void executeWithCompletableFuture_thenApplyAsync() throws ExecutionException, InterruptedException {
        System.out.println("--- executeWithCompletableFuture_thenApplyAsync");
        // CompletableFuture creates ForkJoinPool
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("We are now in thread: " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> future = completableFuture
                .thenApplyAsync(s -> {
                    System.out.println("We are now in thread: " + Thread.currentThread().getName());
                    return s + " World";
                });
        System.out.println(future.get());
        System.out.println();
    }

    public static void executeWithCompletableFuture_thenCombineAsync() throws ExecutionException, InterruptedException {
        System.out.println("--- executeWithCompletableFuture_thenCombineAsync");
        // CompletableFuture uses ForkJoinPool.commonPool()
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("We are now in thread: " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("We are now in thread: " + Thread.currentThread().getName());
            return " World";
        });

        CompletableFuture<String> future = completableFuture1
                .thenCombineAsync(completableFuture2, (s1, s2) -> s1 + s2);
        System.out.println(future.get());
        System.out.println();
    }

    public static void executeWithCompletableFuture_thenComposeAsync() {
        System.out.println("--- executeWithCompletableFuture_thenComposeAsync");
        // CompletableFuture uses ForkJoinPool.commonPool()
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("We are now in thread: " + Thread.currentThread().getName());
            return "Hello";
        });

        CompletableFuture<String> completableFuture2 = completableFuture1.thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> {
            System.out.println("We are now in thread: " + Thread.currentThread().getName());
            return s + " World";
        }));

        completableFuture2.thenAccept(System.out::println);
    }
}
