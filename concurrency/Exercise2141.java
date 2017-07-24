//: concurrency/ActiveObjectDemo.java
package concurrency; /* Added by Eclipse.py */
// Can only pass constants, immutables, "disconnected
// objects," or other active objects as arguments
// to asynch methods.

/* Exercise 21.41
 (6) Add a message handler to ActiveObjectDemo.java that has no return
 value, and call this within main( ).
 */


import java.util.Random;
import java.util.concurrent.*;

import static net.mindview.util.Print.print;

public class Exercise2141 {
  private ExecutorService ex =
    Executors.newSingleThreadExecutor();
  private Random rand = new Random(47);
  // Insert a random delay to produce the effect
  // of a calculation time:
  private void pause(int factor) {
    try {
      TimeUnit.MILLISECONDS.sleep(
        100 + rand.nextInt(factor));
    } catch(InterruptedException e) {
      print("sleep() interrupted");
    }
  }
  void calculateInt(final int x, final int y) {
    ex.execute(new Runnable() {
      public void run() {
        print("starting " + x + " + " + y);
        pause(500);
        print(x + y);
      }
    });
  }

  void calculateFloat(final float x, final float y) {
    ex.execute(new Runnable() {
      public void run() {
        print("starting " + x + " + " + y);
        pause(2000);
        print(x + y);
      }
    });
  }
  public void shutdown() { ex.shutdown(); }
  public static void main(String[] args) {
    Exercise2141 d1 = new Exercise2141();
    // Prevents ConcurrentModificationException:
    for(float f = 0.0f; f < 1.0f; f += 0.2f)
      d1.calculateFloat(f, f);
    for(int i = 0; i < 5; i++)
      d1.calculateInt(i, i);
    print("All asynch calls made");
    d1.shutdown();
  }
} /* Output: (85% match)
All asynch calls made
starting 0.0 + 0.0
starting 0.2 + 0.2
0.0
starting 0.4 + 0.4
0.4
starting 0.6 + 0.6
0.8
starting 0.8 + 0.8
1.2
starting 0 + 0
1.6
starting 1 + 1
0
starting 2 + 2
2
starting 3 + 3
4
starting 4 + 4
6
8
*///:~
