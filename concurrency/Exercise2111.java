//: concurrency/EvenGenerator.java
package concurrency; /* Added by Eclipse.py */
// When threads collide.

/* Exercise 21.11
 (3) Create a class containing two data fields, and a method that manipulates
 those fields in a multistep process so that, during the execution of that method, those fields
 are in an "improper state" (according to some definition that you establish). Add methods to
 read the fields, and create multiple threads to call the various methods and show that the
 data is visible in its "improper state." Fix the problem using the synchronized keyword.
 */


import java.util.concurrent.*;

import static net.mindview.util.Print.print;

public class Exercise2111 {
  private volatile boolean equal = true;
  private int a = 0;
  private int b = 0;

  public synchronized void increase() {
    a ++;
    Thread.yield();
    b ++;
  }

  public synchronized boolean equals() {
    if (a != b)
      equal = false;
    return equal;
  }

  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    Exercise2111 me = new Exercise2111();
    for (int i = 0; i < 10; i ++) {
      exec.execute(new Runnable() {
        @Override
        public void run() {
          while (me.equals())
            me.increase();
          print("Not equal");
        }
      });
    }
    exec.shutdown();
  }
}