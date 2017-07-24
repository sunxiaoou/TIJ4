//: concurrency/waxomatic/WaxOMatic.java
// Basic task cooperation.
package concurrency;

/* Exercise 21.23
 (7) Demonstrate that WaxOMatic.java works successfully when you use
 notify( ) instead of notifyAll( ).
 */


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

class Car2 {
  private boolean waxOn = false;
  synchronized void waxed() {
    waxOn = true; // Ready to buff
    // notifyAll();
    notify();
  }
  synchronized void buffed() {
    waxOn = false; // Ready for another coat of wax
    // notifyAll();
    notify();
  }
  synchronized void waitForWaxing()
  throws InterruptedException {
    while(! waxOn)
      wait();
  }
  synchronized void waitForBuffing()
  throws InterruptedException {
    while(waxOn)
      wait();
  }
}

class WaxOn implements Runnable {
  private Car2 car;
  WaxOn(Car2 c) { car = c; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        printnb("Wax On! ");
        TimeUnit.MILLISECONDS.sleep(200);
        car.waxed();
        car.waitForBuffing();
      }
    } catch(InterruptedException e) {
      print("Exiting via interrupt");
    }
    print("Ending Wax On task");
  }
}

class WaxOff implements Runnable {
  private Car2 car;
  WaxOff(Car2 c) { car = c; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        car.waitForWaxing();
        printnb("Wax Off! ");
        TimeUnit.MILLISECONDS.sleep(200);
        car.buffed();
      }
    } catch(InterruptedException e) {
      print("Exiting via interrupt");
    }
    print("Ending Wax Off task");
  }
}

public class Exercise2123 {
  public static void main(String[] args) throws Exception {
    Car2 car = new Car2();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new WaxOff(car));
    exec.execute(new WaxOn(car));
    TimeUnit.SECONDS.sleep(5); // Run for a while...
    exec.shutdownNow(); // Interrupt all tasks
  }
} /* Output: (95% match)
Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Exiting via interrupt
Ending Wax On task
Exiting via interrupt
Ending Wax Off task
*///:~
