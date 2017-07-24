//: concurrency/OrnamentalGarden.java
package concurrency; /* Added by Eclipse.py */

/*  Exercise 21.19
 (4) Modify OrnamentalGarden.java so that it uses interrupt( ).
 */

import java.util.*;
import java.util.concurrent.*;

import static net.mindview.util.Print.*;

public class Exercise2119 {
  private static Count count = new Count();
  private static List<Entrance> entrances = new ArrayList<>();

  class Entrance implements Runnable {
    private int number = 0;
    // Doesn't need synchronization to read:
    private final int id;

    Entrance(int id) {
      this.id = id;
      // Keep this task in a list. Also prevents
      // garbage collection of dead tasks:
      entrances.add(this);
    }

    public void run() {
      while (! Thread.interrupted()) {
        synchronized (this) {
          ++ number;
        }
        print(this + " Total: " + count.increment());
        try {
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
          print("sleep interrupted");
          break;
        }
      }
      print("Stopping " + this);
    }

    public synchronized int getValue() { return number; }
    public String toString() { return "Entrance " + id + ": " + getValue(); }
  }

  private static int getTotalCount() {
    return count.value();
  }

  private static int sumEntrances() {
    int sum = 0;
    for(Entrance entrance : entrances)
      sum += entrance.getValue();
    return sum;
  }

  private Exercise2119() throws InterruptedException {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < 5; i ++)
      exec.execute(new Entrance(i));
    // Run for a while, then stop and collect the data:
    TimeUnit.SECONDS.sleep(3);
    exec.shutdownNow();
    if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
      print("Some tasks were not terminated!");
    print("Total: " + getTotalCount());
    print("Sum of Entrances: " + sumEntrances());
  }

  public static void main(String[] args) throws Exception {
    new Exercise2119();
  }
}