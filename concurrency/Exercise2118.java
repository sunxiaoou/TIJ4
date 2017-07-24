//: concurrency/SyncObject.java
package concurrency; /* Added by Eclipse.py */
// Synchronizing on another object.

/* Exercise 21.18
 Create a non-task class with a method that calls sleep( ) for a long
 interval. Create a task that calls the method in the non-task class. In main( ), start the task,
 then call interrupt( ) to terminate it. Make sure that the task shuts down safely.
 */


import java.util.concurrent.*;
import static net.mindview.util.Print.*;

class Sleeper2 {
  void sleep() throws InterruptedException {
    TimeUnit.SECONDS.sleep(10);
  }
}

public class Exercise2118 {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    // exec.execute(new Runnable() {
    Future<?> f = exec.submit(new Runnable() {
      @Override
      public void run() {
        try {
          new Sleeper2().sleep();
        }
        catch(InterruptedException e) {
          print("InterruptedException");
        }
        print("Exiting Task");
      }
    });
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    // exec.shutdownNow();
    f.cancel(true);
    exec.shutdown();
  }
}