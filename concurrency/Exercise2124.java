//: concurrency/Restaurant.java
package concurrency; /* Added by Eclipse.py */
// The producer-consumer approach to task cooperation.

/* Exercise 21.24
 (1) Solve a single-producer, single-consumer problem using wait( ) and
 notifyAll( ). The producer must not overflow the receiver’s buffer, which can happen if the
 producer is faster than the consumer. If the consumer is faster than the producer, then it
 must not read the same data more than once. Do not assume anything about the relative
 speeds of the producer or consumer.
*/


import java.util.concurrent.*;

import static net.mindview.util.Print.*;

class Product {
  private static int count = 0;
  private final int orderNum = count ++;
  public String toString() { return "prod " + orderNum; }
}

public class Exercise2124 {
  private Product prod = null;
  private final Consumer consumer = new Consumer();
  private final Producer producer = new Producer();

  class Consumer implements Runnable {
    public void run() {
      try {
        while(!Thread.interrupted()) {
          synchronized(this) {
            while(prod == null)
              wait(); // ... for the chef to produce a meal
          }
          print("Consumer got " + prod);
          synchronized(producer) {
            prod = null;
            producer.notifyAll(); // Ready for another
          }
        }
      } catch(InterruptedException e) {
        print("Consumer interrupted");
      }
    }
  }

  class Producer implements Runnable {
    public void run() {
      try {
        while(!Thread.interrupted()) {
          synchronized(this) {
            while(prod != null)
              wait(); // ... for the prod to be taken
          }
          printnb("Order up! ");
          synchronized(consumer) {
            prod = new Product();
            consumer.notifyAll();
          }
          TimeUnit.MILLISECONDS.sleep(100);
        }
      } catch(InterruptedException e) {
        print("Producer interrupted");
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    Exercise2124 me = new Exercise2124();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(me.consumer);
    exec.execute(me.producer);

    TimeUnit.SECONDS.sleep(1); // Run for a while...
    exec.shutdownNow(); // Interrupt all tasks
  }
}