//: concurrency/Restaurant.java
package concurrency; /* Added by Eclipse.py */
// The producer-consumer approach to task cooperation.

/* Exercise 21.26
 (8) Add a BusBoy class to Restaurant.java. After the meal is delivered,
 the WaitPerson should notify the BusBoy to clean up.
 */

import java.util.concurrent.*;
import static net.mindview.util.Print.*;

public class Exercise2126 {
  private Meal meal;
  private boolean cleaned = true;
  private final WaitPerson waitPerson = new WaitPerson();
  private final Chef chef = new Chef();
  private final BusBoy boy = new BusBoy();
  ExecutorService exec = Executors.newCachedThreadPool();

  class WaitPerson implements Runnable {
    public void run() {
      try {
        while(!Thread.interrupted()) {
          synchronized(this) {
            while(meal == null)
              wait(); // ... for the chef to produce a meal
          }
          print("Waitperson got " + meal);
          synchronized(chef) {
            meal = null;
            chef.notifyAll(); // Ready for another
          }
          synchronized(boy) {
            cleaned = false;
            boy.notifyAll();
          }
        }
      } catch(InterruptedException e) {
        print("WaitPerson interrupted");
      }
    }
  }

  class Chef implements Runnable {
    private int count = 0;
    public void run() {
      try {
        while(!Thread.interrupted()) {
          synchronized(this) {
            while(meal != null)
              wait(); // ... for the meal to be taken
          }
          if(++count == 10) {
            print("Out of food, closing");
            exec.shutdownNow();
          }
          printnb("Order up! ");
          synchronized(waitPerson) {
            meal = new Meal(count);
            waitPerson.notifyAll();
          }
          TimeUnit.MILLISECONDS.sleep(100);
        }
      } catch(InterruptedException e) {
        print("Chef interrupted");
      }
    }
  }

  class BusBoy implements Runnable {
    public void run() {
      try {
        while(!Thread.interrupted()) {
          synchronized(this) {
            while(cleaned)
              wait(); // ... for the meal to be taken
          }
          print("Cleaning");
          synchronized (waitPerson) {
            cleaned = true;
          }
          // TimeUnit.MILLISECONDS.sleep(100);
        }
      } catch(InterruptedException e) {
        print("BusBoy interrupted");
      }
    }
  }

  private Exercise2126() {
    exec.execute(chef);
    exec.execute(boy);
    exec.execute(waitPerson);
  }

  public static void main(String[] args) {
    new Exercise2126();
  }
}