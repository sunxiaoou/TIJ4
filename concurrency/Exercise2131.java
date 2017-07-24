//: concurrency/DeadlockingDiningPhilosophers.java
package concurrency; /* Added by Eclipse.py */
// Demonstrates how deadlock can be hidden in a program.
// {Args: 0 5 timeout}

/* Exercise 21.31
 (8) Change DeadlockingDiningPhilosophers.java so that when a
 philosopher is done with its chopsticks, it drops them into a bin. When a philosopher wants
 to eat, it takes the next two available chopsticks from the bin. Does this eliminate the
 possibility of deadlock? Can you reintroduce deadlock by simply reducing the number of
 available chopsticks?
 */

import java.util.Random;
import java.util.concurrent.*;

import static net.mindview.util.Print.*;

public class Exercise2131 {
  private static int ponderFactor;
  private static ArrayBlockingQueue<Chopstick> chopsticks;

  static class Chopstick {
    private static int count = 0;
    private final int id = count ++;
    public String toString() { return String.format("chopstick(%02d)", id); }
  }

  static class Philosopher implements Runnable {
    private static int count = 0;
    private final int id = count ++;
    private Chopstick left;
    private Chopstick right;
    private Random rand = new Random(47);
    private void pause() throws InterruptedException {
      if (ponderFactor == 0)
        return;
      TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }
    public void run() {
      try {
        while(!Thread.interrupted()) {
          print(this + " " + "thinking");
          pause();
          // Philosopher becomes hungry
          print(this + " " + "grabbing right");
          right = chopsticks.take();
          Thread.yield();
          print(this + " " + "grabbing left");
          left = chopsticks.take();
          print(this + " " + "eating with " + right + " " + left);
          pause();
          chopsticks.put(right);
          chopsticks.put(left);
        }
      } catch(InterruptedException e) {
        print(this + " " + "exiting via interrupt");
      }
    }
    public String toString() { return "Philosopher " + id; }
  }

  private Exercise2131(int size, int ponder) throws InterruptedException {
    ponderFactor = ponder;
    chopsticks = new ArrayBlockingQueue<>(size);
    for(int i = 0; i < size; i ++)
      chopsticks.put(new Chopstick());

    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < size; i ++)
      exec.execute(new Philosopher());
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
  }

  public static void main(String[] args) throws Exception {
    new Exercise2131(5, 5);
  }
}