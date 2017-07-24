//: concurrency/CallableDemo.java
package concurrency; /* Added by Eclipse.py */

/* Exercise 21.10
 (4) Modify Exercise 5 following the example of the ThreadMethod class,
 so that runTask( ) takes an argument of the number of Fibonacci numbers to sum, and each
 time you call runTask( ) it returns the Future produced by the call to submit( ).
 */


import java.util.ArrayList;
import java.util.concurrent.*;

import static net.mindview.util.Print.print;

class FibonacciSum2 {
  private static ExecutorService exec = Executors.newCachedThreadPool();
  Future<String> runTask(int id, int number) {
    return exec.submit(new Callable<String>() {
      public String call() {
        Fibonacci gen = new Fibonacci();
        int sum = 0;
        for(int i = 0; i < number; i++) {
          sum += gen.next();
        }
        // System.out.printf("%d: %d\n", id, sum);
        return id + ": " + sum;
      }
    });
  }
  static void shutdown() { exec.shutdown(); }
}

public class Exercise2110 {
  public static void main(String[] args) {
    ArrayList<Future<String>> results = new ArrayList<>();
    for(int i = 0; i < 10; i ++)
      results.add(new FibonacciSum2().runTask(i, 18));
    for(Future<String> fs : results)
      try {
        // get() blocks until completion:
        print(fs.get());
      } catch(InterruptedException e) {
        print(e);
        return;
      } catch(ExecutionException e) {
        print(e);
      } finally {
        FibonacciSum2.shutdown();
      }
  }
}