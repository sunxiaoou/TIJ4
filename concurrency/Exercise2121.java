//: concurrency/waxomatic/WaxOMatic.java
// Basic task cooperation.
package concurrency;

/* Exercise 21.21
 (2) Create two Runnables, one with a run( ) that starts and calls wait( ).
 The second class should capture the reference of the first Runnable object. Its run( )
 should call notifyAll( ) for the first task after some number of seconds have passed so that
 the first task can display a message. Test your classes using an Executor.
*/


import java.util.concurrent.*;

import static net.mindview.util.Print.*;

class Wait implements Runnable {
  public synchronized void run() {
    try {
      wait();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    print("Exit wait()");
  }
}

class Notify implements Runnable {
  private final Wait wait;
  Notify(Wait wait) { this.wait = wait; }
  public synchronized void run() {
    try {
      TimeUnit.SECONDS.sleep(3); // Run for a while...
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    synchronized (wait) {
      print("Notify");
      wait.notifyAll();
    }
  }
}

public class Exercise2121 {
  public static void main(String[] args) throws InterruptedException {
    Wait wait = new Wait();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(wait);
    exec.execute(new Notify(wait));
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
  }
}