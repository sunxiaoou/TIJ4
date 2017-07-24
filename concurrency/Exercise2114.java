//: concurrency/AtomicIntegerTest.java
package concurrency; /* Added by Eclipse.py */

/* Exercise 21.14:
 (4) Demonstrate that java.util.Timer scales to large numbers by creating
 a program that generates many Timer objects that perform some simple task when the
 timeout completes.
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static net.mindview.util.Print.print;


public class Exercise2114 {
  public static void main(String[] args) throws InterruptedException {
    try {
      for (int i = 0; i < 10; i++) {
        int finalI = i;
        new Timer().schedule(new TimerTask() {
          public void run() {
            print(finalI + " Completed");
            // System.exit(0);
          }
        }, 2000); // Terminate after 5 seconds
      }
    } finally {
      TimeUnit.SECONDS.sleep(3);
      print("Bye!");
      System.exit(0);
    }
  }
}
