//: arrays/PrimitiveConversionDemonstration.java
package arrays; /* Added by Eclipse.py */

/* Exercise 16.23
 (2) Create an array of Integer, fill it with random int values (using
 autoboxing), and sort it into reverse order using a Comparator.
 */


import java.util.Arrays;
import java.util.Collections;
import net.mindview.util.*;

import static net.mindview.util.Print.*;


public class Exercise1623 {
  public static void main(String[] args) {
    Integer[] a = Generated.array(Integer.class,
            new RandomGenerator.Integer(1000), 15);
    print(Arrays.toString(a));
    Arrays.sort(a, Collections.reverseOrder());
    print(Arrays.toString(a));
  }
}
