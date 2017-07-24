//: concurrency/waxomatic/WaxOMatic.java
// Basic task cooperation.
package concurrency;

/* Exercise 21.22
 (4) Create an example of a busy wait. One task sleeps for a while and then
 sets a flag to true. The second task watches that flag inside a while loop (this is the busy
 wait) and when the flag becomes true, sets it back to false and reports the change to the
 console. Note how much wasted time the program spends inside the busy wait, and create a
 second version of the program that uses wait( ) instead of the busy wait.
*/


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static net.mindview.util.Print.print;

class Flag implements Runnable {
  boolean f = false;
  Watcher w = null;
  public synchronized void run() {
    try {
      while (true) {
        TimeUnit.SECONDS.sleep(1);
        f = true;
        if (w != null) {
          synchronized (w) {
            w.notifyAll();
          }
        }
      }
    } catch (InterruptedException e) {
      print("InterruptedException");
    }
    print("Exit Flag.run()");
  }
}

class Watcher implements Runnable {
  private Flag flag;
  Watcher(Flag flag) {
    this.flag = flag;
    flag.w = this;
  }
  public synchronized void run() {
    try {
      while (true) {
        /*
        TimeUnit.MILLISECONDS.sleep(10);
        if (flag.f) {
          print("flag.f is true");
          flag.f = false;
        }
        */
        wait();
        if (flag.f) {
          print("flag.f is true");
          flag.f = false;
        }
      }
    } catch (InterruptedException e) {
      print("InterruptedException");
    }
    print("Exit Watcher.run()");
  }
}


public class Exercise2122 {
  public static void main(String[] args) throws InterruptedException {
    Flag flag = new Flag();
    Watcher watcher = new Watcher(flag);
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(flag);
    exec.execute(watcher);
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
  }
}