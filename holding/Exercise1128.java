//: holding/PriorityQueueDemo.java
package holding; /* Added by Eclipse.py */

/* Exercise 11.28:
 Fill a PriorityQueue (using offer( )) with Double values created
 using java.util.Random, then remove the elements using poll( ) and display them.
*/

import java.util.*;

public class Exercise1128 {
  public static void main(String[] args) {
    // PriorityQueue<Float> priorityQueue = new PriorityQueue<>();
    PriorityQueue<Float> priorityQueue = new PriorityQueue<>(10, Collections.reverseOrder());
    Random rand = new Random(47);
    for(int i = 0; i < 10; i++)
      priorityQueue.offer(rand.nextFloat());

    Float F;
    while ((F = priorityQueue.poll()) != null)
      System.out.print(F + " ");
    System.out.println();
  }
}