//: net/mindview/util/ConvertTo.java
package arrays;

/* Exercise 16.11:
 (2) Show that autoboxing doesn’t work with arrays.
 */


public class Exercise1611 {
  static int[] primitive(Integer[] in) {
    int[] result = new int[in.length];
    for (int i = 0; i < in.length; i++)
      result[i] = in[i];
    return result;
  }

  public static void main(String args[]) {
    Integer integers[] = new Integer[]{1, 2, 3};
    // int ints[] = integers;
    int ints[] = primitive(integers);
    // Integer integer2[] = ints;
  }
}
