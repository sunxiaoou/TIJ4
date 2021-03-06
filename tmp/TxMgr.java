package tmp;

/*
 ThreadLocal and InheritableThreadLocal Simple Examples
 Class ThreadLocal provides thread-local variables - variables that can only be read and written by the same thread.
 Lets consider simple example of using ThreadLocal.
 We have a TransactionManager class that provides static methods to:
 Start a transaction with a generated ID
 Store that ID as a static field and provide a transaction-ID-getter method to other code logic that needs to know the
 current transaction ID
 In a single-threaded environment, TransactionManager can simply store the ID as a static field and return it as is.
 However, this will certainly not work in a multiple-threaded environment. Imagine if multiple threads used
 TransactionManager. Transaction IDs generated by each thread could overwrite each other since there is only one static
 instance of transaction ID. To solve this problem we can use ThreadLocal.
 IDs for subworkers are null because context initialized for every new thread, even if this new thred in a child thread
 . To allow child threads read/write thread-local variable of parent was provided class InheritableThreadLocal, which
 is a subclass of ThreadLocal. Just change in example above implementation of ThreadLocal to InheritableThreadLocal.
*/


import net.mindview.util.*;

class TransactionManager {
    private static RandomGenerator.String gen = new RandomGenerator.String(7);
    // private static final ThreadLocal<String> context = new ThreadLocal<>();
    private static final ThreadLocal<String> context = new InheritableThreadLocal<>();
    static void startTransaction() { context.set(gen.next()); }
    static String getTransactionId() {
        return context.get();
    }
    static void endTransaction() { context.remove(); }
}

class Worker implements Runnable {
    private final String name;
    Worker(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        TransactionManager.startTransaction();
        System.out.println(name + " after start - " + TransactionManager.getTransactionId());
        SubWorker subWorker = new SubWorker("sub" + name);
        Thread subWorkerThread = new Thread(subWorker);
        subWorkerThread.start();
        try {
            subWorkerThread.join();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        TransactionManager.endTransaction();
        System.out.println(name + " after end - " + TransactionManager.getTransactionId());
    }
}

class SubWorker implements Runnable {
    private final String name;
    SubWorker(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        System.out.println(name + " - " + TransactionManager.getTransactionId());
    }
}

public class TxMgr {
    public static void main(String[] args) {
        new Thread(new Worker("worker1")).start();
        new Thread(new Worker("worker2")).start();
    }
}
