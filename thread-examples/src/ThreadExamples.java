import java.util.function.Consumer;

public final class ThreadExamples {
    private ThreadExamples() {
    }

    public static void startThreadWithRunnable() throws InterruptedException {
        System.out.println("--- startThreadWithRunnable");
        Thread thread = new Thread(() -> System.out.println("We are now in thread: " + Thread.currentThread().getName()));

        startThread(thread);
    }

    public static void startInheritedThreadWithRunnable() throws InterruptedException {
        System.out.println("--- startInheritedThreadWithRunnable");
        class MyThread extends Thread {
            @Override
            public void run() {
                System.out.println("We are now in thread: " + Thread.currentThread().getName());
            }
        }
        Thread thread = new MyThread();

        startThread(thread);
    }

    public static void startThreadWithRunnableAndCallback() throws InterruptedException {
        System.out.println("--- startThreadWithRunnableAndCallback");
        Consumer<String> callback = (String threadName) -> System.out.println("We are now in thread: " + threadName);
        Thread thread = new Thread(() -> callback.accept(Thread.currentThread().getName()));
        startThread(thread);
    }

    private static void startThread(Thread thread) throws InterruptedException {
        thread.setName("Thread-1");

        System.out.println("We are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
        thread.start();
        System.out.println("We are in thread: " + Thread.currentThread().getName() + " after starting a new thread");
        Thread.sleep(100);
        System.out.println();
    }

    public static void joinThread() throws InterruptedException {
        System.out.println("--- joinThreads");
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100);
                System.out.println("Thread: " + Thread.currentThread().getName() + " finished execution");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.setName("Thread-1");
        thread.start();
        thread.join();
        System.out.println();
    }

    public static void interruptThread() throws InterruptedException {
        System.out.println("--- interruptThread");
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("Thread: " + Thread.currentThread().getName() + " is doing some work");
                // we should check isInterrupted() to terminate a child thread, otherwise it can run forever (if the child thread is not a daemon)
                // or until the main thread finishes execution (if the child thread is a daemon)
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " is interrupted");
                    break;
                }
            }
        });

        thread.setName("Thread-1");
        thread.start();
        thread.interrupt();
        Thread.sleep(100);
        System.out.println();
    }

    public static void interruptedException() {
        System.out.println("--- interruptedException");
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Thread: " + Thread.currentThread().getName() + " is interrupted");
            }
        });

        thread.setName("Thread-1");
        thread.start();
        thread.interrupt();
    }
}
