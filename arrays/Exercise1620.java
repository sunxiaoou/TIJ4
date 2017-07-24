//: arrays/MultidimensionalPrimitiveArray.java
package arrays; /* Added by Eclipse.py */
// Creating multidimensional arrays.

/* Exercise 16.20
 (4) Demonstrate deepEquals( ) for multidimensional arrays.
 */

import java.util.Arrays;

import static net.mindview.util.Print.*;

public class Exercise1620 {
  public static void main(String[] args) {
    int[][] a = { {1, 2, 3, }, {4, 5, 6, } };
    int[][] b = { {1, 2, 3, }, {4, 5, 6, } };

    print(Arrays.equals(a, b));
    print(Arrays.deepEquals(a, b));
  }
}