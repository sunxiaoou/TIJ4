//: strings/SplitDemo.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.14
 (1) Rewrite SplitDemo using String.split( ).
 */

import java.util.Arrays;

import static net.mindview.util.Print.print;

public class Exercise1314 {
  public static void main(String[] args) {
    String input =
      "This!!unusual use!!of exclamation!!points";
    print(Arrays.toString(input.split("!!")));
    // Only do the first three:
    print(Arrays.toString(input.split("!!", 3)));
  }
} /* Output:
[This, unusual use, of exclamation, points]
[This, unusual use, of exclamation!!points]
*///:~
