//: concurrency/waxomatic/WaxOMatic.java
// Basic task cooperation.
package concurrency;

/* Exercise 21.42
 (7) Modify WaxOMatic.java so that it implements active objects.
 */


import java.util.concurrent.*;
import static net.mindview.util.Print.*;


public class Exercise2142 {
  class Car {
    private boolean waxOn = false;
    synchronized void waxed() {
      waxOn = true; // Ready to buff
      notifyAll();
    }
    synchronized void buffed() {
      waxOn = false; // Ready for another coat of wax
      notifyAll();
    }
    synchronized void waitForWaxing() throws InterruptedException {
      while(! waxOn)
        wait();
    }
    synchronized void waitForBuffing() throws InterruptedException {
      while(waxOn)
        wait();
    }
  }

  class WaxOn implements Runnable {
    private Car car;
    WaxOn(Car c) { car = c; }
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
    private Car car;
    WaxOff(Car c) { car = c; }
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

  private Exercise2142() throws InterruptedException {
    Car car = new Car();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new WaxOff(car));
    exec.execute(new WaxOn(car));
    TimeUnit.SECONDS.sleep(5); // Run for a while...
    exec.shutdownNow(); // Interrupt all tasks
  }

  public static void main(String[] args) throws Exception { new Exercise2142(); }
} /* Output: (95% match)
Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Wax Off! Wax On! Exiting via interrupt
Ending Wax On task
Exiting via interrupt
Ending Wax Off task
*///:~
