//: concurrency/CachedThreadPool.java
package concurrency; /* Added by Eclipse.py */

/*  Exercise 21.06
 (2) Create a task that sleeps for a random amount of time between 1 and 10
 seconds, then displays its sleep time and exits. Create and run a quantity (given on the
 command line) of these tasks.
*/


import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static net.mindview.util.Print.printf;


class Sleep implements Runnable {
  private static int taskCount = 0;
  private final int id = taskCount ++;
  private static Random rand = new Random(47);
  public void run() {
    try {
      int r = rand.nextInt(10) + 1;
      TimeUnit.MILLISECONDS.sleep(r * 1000);
      printf("#%d: Sleep(%d)\n", id, r);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

public class Exercise2106 {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < 5; i++)
      exec.execute(new Sleep());
    exec.shutdown();
  }
}