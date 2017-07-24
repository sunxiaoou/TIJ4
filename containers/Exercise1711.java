//: containers/QueueBehavior.java
package containers; /* Added by Eclipse.py */
// Compares the behavior of some of the queues

/* Exercise 17.11
 (2) Create a class that contains an Integer that is initialized to a value
 between o and 100 using java.util.Random. Implement Comparable using this Integer
 field. Fill a PriorityQueue with objects of your class, and extract the values using poll( ) to
 show that it produces the expected order.
 */


import net.mindview.util.Generator;

import java.util.*;


class AnInt implements Comparable<AnInt> {
  private Integer anint;

  AnInt(Integer i) { anint = i; }

  public String toString() { return Integer.toString(anint); }

  @Override
  public int compareTo(AnInt arg) {
    return (arg.anint > anint ? -1 : (arg.anint == anint ? 0 : 1));
  }

  static class Gen implements Generator<AnInt> {
    private Random rand = new Random(47);
    public AnInt next() { return new AnInt(rand.nextInt(100)); }
  }
}

public class Exercise1711 {
  private static int count = 10;
  static <T> void test(Queue<T> queue, Generator<T> gen) {
    for(int i = 0; i < count; i++)
      queue.offer(gen.next());
    while(queue.peek() != null)
      System.out.print(queue.remove() + " ");
    System.out.println();
  }
  public static void main(String[] args) {
    test(new LinkedList<AnInt>(), new AnInt.Gen());
    test(new PriorityQueue<AnInt>(), new AnInt.Gen());
  }
}