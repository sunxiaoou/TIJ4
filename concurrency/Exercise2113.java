//: concurrency/SerialNumberChecker.java
package concurrency; /* Added by Eclipse.py */
// Operations that may seem safe are not,
// when threads are present.
// {Args: 4}

/* Exercise 21.13
 (1) Repair SerialNumberChecker.java using the synchronized
 keyword. Can you demonstrate that it is now correct?
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class SerialNumberGenerator2 {
  private static volatile int serialNumber = 0;
  public static synchronized int nextSerialNumber() {
    return serialNumber++; // Not thread-safe
  }
}

public class Exercise2113 {
  private static final int SIZE = 10;
  private static CircularSet serials = new CircularSet(1000);
  private static ExecutorService exec = Executors.newCachedThreadPool();
  static class SerialChecker implements Runnable {
    public void run() {
      while(true) {
        int serial = SerialNumberGenerator2.nextSerialNumber();
        if(serials.contains(serial)) {
          System.out.println("Duplicate: " + serial);
          System.exit(0);
        }
        serials.add(serial);
      }
    }
  }
  public static void main(String[] args) throws Exception {
    for(int i = 0; i < SIZE; i++)
      exec.execute(new SerialChecker());
    // Stop after n seconds if there's an argument:
    if(args.length > 0) {
      TimeUnit.SECONDS.sleep(new Integer(args[0]));
      System.out.println("No duplicates detected");
      System.exit(0);
    }
  }
}
