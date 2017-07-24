//: concurrency/TestBlockingQueues.java
package concurrency; /* Added by Eclipse.py */
// {RunByHand}

/* Exercise 21.28
 (3) Modify TestBlockingQueues.java by adding a new task that places
 LiftOff on the BlockingQueue, instead of doing it in main( ).
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;

import static net.mindview.util.Print.print;

public class Exercise2128 {
  private BlockingQueue<LiftOff> rockets;

  class LiftOffRunner implements Runnable {
    public void run() {
      try {
        while(!Thread.interrupted()) {
          LiftOff rocket = rockets.take();
          rocket.run(); // Use this thread
        }
      } catch(InterruptedException e) {
        print("Waking from take()");
      }
      print("Exiting LiftOffRunner");
    }
  }

  class LiftOffPreparer implements Runnable {
    public void run() {
      for (int i = 0; i < 5; i++) {
        try {
          rockets.put(new LiftOff(5));
        } catch (InterruptedException e) {
          print("Interrupted during put()");
        }
      }
    }
  }

  private static void getkey() {
    try {
      // Compensate for Windows/Linux difference in the
      // length of the result produced by the Enter key:
      new BufferedReader(
        new InputStreamReader(System.in)).readLine();
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  private static void getkey(String message) {
    print(message);
    getkey();
  }

  private Exercise2128(String msg, BlockingQueue<LiftOff> queue) {
    ExecutorService exec = Executors.newCachedThreadPool();

    rockets = queue;
    print(msg);
    exec.execute(new LiftOffPreparer());
    exec.execute(new LiftOffRunner());
    getkey("Press 'Enter' (" + msg + ")");
    exec.shutdownNow();
    print("Finished " + msg + " test");
  }

  public static void main(String[] args) {
    /* Unlimited size*/
    new Exercise2128("LinkedBlockingQueue", new LinkedBlockingQueue<>());
    // Fixed size
    new Exercise2128("ArrayBlockingQueue", new ArrayBlockingQueue<>(3));
    // Size of 1
    new Exercise2128("SynchronousQueue", new SynchronousQueue<>());
  }
}