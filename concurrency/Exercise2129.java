//: concurrency/ToastOMatic.java
package concurrency; /* Added by Eclipse.py */
// A toaster that uses queues.

/* Exercise 21.29
 (8) Modify ToastOMatic.java to create peanut butter and jelly on toast
 sandwiches using two separate assembly lines (one for peanut butter, the second for jelly,
 then merging the two lines).
 */


import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

public class Exercise2129 {
  private LinkedBlockingQueue<Toast>
          butterIn = new LinkedBlockingQueue<>(),
          butterOut = new LinkedBlockingQueue<>(),
          jellyIn = new LinkedBlockingQueue<>(),
          jellyOut = new LinkedBlockingQueue<>();

  static class Toast {
    public enum Status { DRY, BUTTERED, JAMMED }
    private Status status = Status.DRY;
    private static int count = 0;
    private final int id = count ++;
    void butter() { status = Status.BUTTERED; }
    void jam() { status = Status.JAMMED; }
    Status getStatus() { return status; }
    public int getId() { return id; }
    public String toString() { return String.format("Toast%02d(%s)", id, status); }
  }

  class Sandwich {
    Toast top, bottom;
    Sandwich(Toast top, Toast bottom) {
      this.top = top;
      this.bottom = bottom;
    }
    public String toString() { return top + " + " + bottom; }
  }

  class Toaster implements Runnable {
    private Random rand = new Random(47);
    public void run() {
      try {
        while (! Thread.interrupted()) {
          TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
          Toast t = new Toast();
          print(t);
          butterIn.put(t);
          t = new Toast();
          print(t);
          jellyIn.put(t);
        }
      } catch(InterruptedException e) {
        print("Toaster interrupted");
      }
      print("Toaster off");
    }
  }

  class Butterer implements Runnable {
    public void run() {
      try {
        while(!Thread.interrupted()) {
          // Blocks until next piece of toast is available:
          Toast t = butterIn.take();
          t.butter();
          print(t);
          butterOut.put(t);
        }
      } catch(InterruptedException e) {
        print("Bufferer interrupted");
      }
      print("Bufferer off");
    }
  }

  class Jammer implements Runnable {
    public void run() {
      try {
        while(!Thread.interrupted()) {
          // Blocks until next piece of toast is available:
          Toast t = jellyIn.take();
          t.jam();
          print(t);
          jellyOut.put(t);
        }
      } catch(InterruptedException e) {
        print("Jammer interrupted");
      }
      print("Jammer off");
    }
  }

  class Sandwicher implements Runnable {
    public void run() {
      try {
        while(!Thread.interrupted()) {
          Sandwich s = new Sandwich(butterOut.take(), jellyOut.take());
          print(s);
        }
      } catch(InterruptedException e) {
        print("Sandwicher interrupted");
      }
      print("Sandwicher off");
    }
  }

  private Exercise2129() throws InterruptedException {
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new Toaster());
    exec.execute(new Butterer());
    exec.execute(new Jammer());
    exec.execute(new Sandwicher());
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
  }

  public static void main(String[] args) throws Exception {
    new Exercise2129();
  }
}