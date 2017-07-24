//: concurrency/CallableDemo.java
package concurrency; /* Added by Eclipse.py */

/* Exercise 21.02
 (2) Following the form of generics/Fibonacci.java, create a task that
 produces a sequence of n Fibonacci numbers, where n is provided to the constructor of the
 task. Create a number of these tasks and drive them using threads.
 */

/* Exercise 21.05
 (2) Modify Exercise 2 so that the task is a Callable that sums the values of
 all the Fibonacci numbers. Create several tasks and display the results.
 */

import net.mindview.util.Generator;

import java.util.ArrayList;
import java.util.concurrent.*;

import static net.mindview.util.Print.print;

class Fibonacci implements Generator<Integer> {
  private int count = 0;
  public Integer next() { return fib(count++); }
  private int fib(int n) {
    if (n < 2) return 1;
    return fib(n - 2) + fib(n - 1);
  }
}

class FibonacciSum implements Callable<String> {
  private static int taskCount = 0;
  private final int id = taskCount ++;
  private int number;
  FibonacciSum(int number) { this.number = number; }
  public String call() {
    Fibonacci gen = new Fibonacci();
    int sum = 0;
    for(int i = 0; i < number; i++) {
      sum += gen.next();
    }
    // System.out.printf("%d: %d\n", id, sum);
    return id + ": " + sum;
  }
}

public class Exercise2105 {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    ArrayList<Future<String>> results = new ArrayList<>();
    for(int i = 0; i < 10; i ++)
      results.add(exec.submit(new FibonacciSum(18)));
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
        exec.shutdown();
      }
  }
}