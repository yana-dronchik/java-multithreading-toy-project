public class Main {
    public static void main(String[] args) throws InterruptedException {
        ThreadExamples.startThreadWithRunnable();
        ThreadExamples.startInheritedThreadWithRunnable();
        ThreadExamples.startThreadWithRunnableAndCallback();
        ThreadExamples.joinThread();
        ThreadExamples.interruptThread();
        ThreadExamples.interruptedException();
    }
}