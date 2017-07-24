//: net/mindview/util/CountingGenerator.java
// Simple generator implementations.
package arrays;

/* Exercise 1616
 (3) Starting with CountingGenerator.java, create a SkipGenerator
 class that produces new values by incrementing according to a constructor argument. Modify
 TestArrayGeneration.java to show that your new class works correctly.
 */

import java.util.Arrays;
import net.mindview.util.*;

import static net.mindview.util.Print.*;

class IntegerGenerator implements Generator<Integer> {
  private int value = 0;
  IntegerGenerator() {}
  IntegerGenerator(int value) { this.value = value; }
  public Integer next() { return value ++; }
}

public class Exercise1616 {
  public static void main(String args[]) {
    int[] ints = ConvertTo.primitive(Generated.array(
            Integer.class, new IntegerGenerator(5), 10));
    print(Arrays.toString(ints));
  }
}