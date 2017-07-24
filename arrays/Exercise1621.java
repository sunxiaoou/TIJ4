//: arrays/ContainerComparison.java
package arrays; /* Added by Eclipse.py */

/* Exercise 16.21
 (3) Try to sort an array of the objects in Exercise 18. Implement
 Comparable to fix the problem. Now create a Comparator to sort the objects into reverse
 order.
 */

import java.util.*;
import net.mindview.util.*;

import static net.mindview.util.Print.*;


class BerylliumSphere2 implements Comparable<BerylliumSphere2> {
  private static long counter;
  private final long id = counter++;
  private int value;

  BerylliumSphere2(int value) { this.value = value; }
  public String toString() { return "Sphere" + id +"(" + value + ")"; }

  public int compareTo(BerylliumSphere2 rv) {
    return (value < rv.value ? -1 : (value == rv.value ? 0 : 1));
  }

  private static Random r = new Random(47);
  public static Generator<BerylliumSphere2> generator() {
    return new Generator<BerylliumSphere2>() {
      public BerylliumSphere2 next() {
        return new BerylliumSphere2(r.nextInt(100));
      }
    };
  }
}

public class Exercise1621 {
  public static void main(String args[]) {
    BerylliumSphere2[] a = Generated.array(new BerylliumSphere2[5], BerylliumSphere2.generator());
    print("before sorting:");
    print(Arrays.toString(a));
    Arrays.sort(a);
    print("after sorting:");
    print(Arrays.toString(a));
  }
}