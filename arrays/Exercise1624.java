//: arrays/PrimitiveConversionDemonstration.java
package arrays; /* Added by Eclipse.py */

/* Exercise 16.24
 (3) Show that the class from Exercise 19 can be searched.
 */


import java.util.Arrays;
import java.util.Random;

import static net.mindview.util.Print.print;

class AnInt3 extends AnInt implements Comparable<AnInt3> {
  public AnInt3(Integer i) { super(i); }
  public int compareTo(AnInt3 rv) {
    return (i < rv.i ? -1 : (i == rv.i ? 0 : 1));
  }
}

public class Exercise1624 {
  static Random rand = new Random(47);
  public static void main(String[] args) {
    AnInt3 ints[] = new AnInt3[10];
    for (int i = 0; i < ints.length; i ++)
      ints[i] = new AnInt3(rand.nextInt(1000));

    print(Arrays.toString(ints));
    Arrays.sort(ints);
    print(Arrays.toString(ints));
    print("555 is at " + Arrays.binarySearch(ints, new AnInt3(555)));
  }
}
