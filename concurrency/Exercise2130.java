//: concurrency/PipedIO.java
package concurrency; /* Added by Eclipse.py */
// Using pipes for inter-task I/O


/* Exercise 21.30
 (1) Modify PipedIO.java to use a BlockingQueue instead of a pipe.
 */


import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

public class Exercise2130 {
  private LinkedBlockingQueue<Character> characters = new LinkedBlockingQueue<>();

  class Sender implements Runnable {
    private Random rand = new Random(47);
    public void run() {
      try {
        while (! Thread.interrupted()) {
          for (char c = 'A'; c <= 'z'; c++) {
            characters.put(c);
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
          }
        }
      } catch(InterruptedException e) {
        print(e + " Sender sleep interrupted");
      }
      print("Sender off");
    }
  }

  class Receiver implements Runnable {
    public void run() {
      try {
        while (! Thread.interrupted()) {
          printnb("Read: " + characters.take() + ", ");
        }
      } catch(InterruptedException e) {
        print(e + " Receiver read exception");
      }
      print("Receiver off");
    }
  }

  private Exercise2130() throws InterruptedException {
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new Sender());
    exec.execute(new Receiver());
    TimeUnit.SECONDS.sleep(4);
    exec.shutdownNow();
  }

  public static void main(String[] args) throws Exception { new Exercise2130(); }
} /* Output: (65% match)
Read: A, Read: B, Read: C, Read: D, Read: E, Read: F, Read: G, Read: H, Read: I, Read: J, Read: K, Read: L, Read: M, java.lang.InterruptedException: sleep interrupted Sender sleep interrupted
java.io.InterruptedIOException Receiver read exception
*///:~
