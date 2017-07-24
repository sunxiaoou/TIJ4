//: concurrency/Restaurant.java
package concurrency; /* Added by Eclipse.py */
// The producer-consumer approach to task cooperation.

/* Exercise 21.27
 (2) Modify Restaurant.java to use explicit Lock and Condition objects.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

public class Exercise2127 {
  private Meal meal;
  private final WaitPerson waitPerson = new WaitPerson();
  private final Chef chef = new Chef();
  ExecutorService exec = Executors.newCachedThreadPool();

  class WaitPerson implements Runnable {
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void run() {
      try {
        while(!Thread.interrupted()) {
          lock.lock();
          try {
            while(meal == null)
              condition.await(); // ... for the chef to produce a meal
          } finally {
            lock.unlock();
          }
          print("Waitperson got " + meal);
          chef.lock.lock();
          try {
            meal = null;
            chef.condition.signalAll(); // Ready for another
          } finally {
            chef.lock.unlock();
          }
        }
      } catch(InterruptedException e) {
        print("WaitPerson interrupted");
      }
    }
  }

  class Chef implements Runnable {
    private int count = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void run() {
      try {
        while(!Thread.interrupted()) {
          lock.lock();
          try {
            while(meal != null)
              wait(); // ... for the meal to be taken
          } finally {
            lock.unlock();
          }
          if(++ count == 10) {
            print("Out of food, closing");
            exec.shutdownNow();
          }
          printnb("Order up! ");
          waitPerson.lock.lock();
          try {
            meal = new Meal(count);
            waitPerson.condition.signalAll();
          } finally {
            waitPerson.lock.unlock();
          }
          TimeUnit.MILLISECONDS.sleep(100);
        }
      } catch(InterruptedException e) {
        print("Chef interrupted");
      }
    }
  }

  private Exercise2127() {
    exec.execute(chef);
    exec.execute(waitPerson);
  }

  public static void main(String[] args) {
    new Exercise2127();
  }
}