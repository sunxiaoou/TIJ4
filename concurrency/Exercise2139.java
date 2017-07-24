//: concurrency/FastSimulation.java
package concurrency; /* Added by Eclipse.py */

/* Exercise 21.39
 (6) Does FastSimulation.java make reasonable assumptions? Try
 changing the array to ordinary ints instead of AtomicInteger and using Lock mutexes.
 Compare the performance between the two versions of the program.
 */

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static net.mindview.util.Print.print;

public class Exercise2139 {
  static final int N_ELEMENTS = 100000;
  static final int N_GENES = 30;
  static final int N_EVOLVERS = 50;
  static final int[][] GRID = new int[N_ELEMENTS][N_GENES];
  static Random rand = new Random(47);
  static CountDownLatch endLatch = new CountDownLatch(N_EVOLVERS);
  static long totalCount = 0;
  static final ReentrantLock[] lock = new ReentrantLock[N_ELEMENTS];

  static class Evolver implements Runnable {
    public void run() {
      long count = 0;
      while(! Thread.interrupted()) {
        // Randomly select an element to work on:
        int element = rand.nextInt(N_ELEMENTS);
        int previous = element - 1;
        if(previous < 0) previous = N_ELEMENTS - 1;
        int next = element + 1;
        if(next >= N_ELEMENTS) next = 0;
        /*
        synchronized (this) {
          for (int i = 0; i < N_GENES; i ++)
            // Perform some kind of modeling calculation:
            GRID[element][i] = (GRID[element][i] + GRID[previous][i] + GRID[next][i]) / 3;
          try {
            TimeUnit.MICROSECONDS.sleep(1);
          } catch (InterruptedException e) {
            break;
          }
        }
        */
        lock[element].lock();
        try {
          for (int i = 0; i < N_GENES; i ++)
            // Perform some kind of modeling calculation:
            GRID[element][i] = (GRID[element][i] + GRID[previous][i] + GRID[next][i]) / 3;
          TimeUnit.MICROSECONDS.sleep(1);
        } catch (InterruptedException e) {
          break;
        } finally {
          lock[element].unlock();
        }
        count ++;
      }
      synchronized (this) {
        totalCount += count;
      }
      endLatch.countDown();
    }
  }

  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < N_ELEMENTS; i++)
      for(int j = 0; j < N_GENES; j++)
        GRID[i][j] = rand.nextInt(1000);
    for(int i = 0; i < N_ELEMENTS; i++)
      lock[i] = new ReentrantLock();
    for(int i = 0; i < N_EVOLVERS; i++)
      exec.execute(new Evolver());
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
    endLatch.await();
    print("totalCount = " + totalCount);
  }
} /* (Execute to see output) *///:~