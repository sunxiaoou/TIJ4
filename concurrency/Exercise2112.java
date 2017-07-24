//: concurrency/AtomicityTest.java
package concurrency; /* Added by Eclipse.py */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* Exercise 21.12
 (3) Repair AtomicityTest.java using the synchronized keyword. Can
 you demonstrate that it is now correct?
 */

public class Exercise2112 implements Runnable {
  private int i = 0;
  public synchronized int getValue() { return i; }
  private synchronized void evenIncrement() { i++; i++; }
  public void run() {
    while(true)
      evenIncrement();
  }
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    Exercise2112 at = new Exercise2112();
    exec.execute(at);
    while(true) {
      int val = at.getValue();
      if(val % 2 != 0) {
        System.out.println(val);
        System.exit(0);
      }
    }
  }
} /* Output: (Sample)
191583767
*///:~
