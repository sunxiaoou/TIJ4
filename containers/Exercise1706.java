//: containers/Unsupported.java
package containers; /* Added by Eclipse.py */
// Unsupported operations in Java containers.

/* Exercise 1706
 (2) Note that List has additional "optional" operations that are not included
 in Collection. Write a version of Unsupported.java that tests these additional optional
 operations.
 */

import java.util.*;

public class Exercise1706 {
  static void test(String msg, List<String> list) {
    System.out.println("--- " + list.getClass().getSimpleName() + " " + msg + " ---");
    List<String> c = list;
    List<String> subList = list.subList(1, 8);
    // Copy of the sublist:
    List<String> c2 = new ArrayList<String>(subList);
    try {
      c.add(c.size(), "X");
    } catch (Exception e) {
      System.out.println("add(): " + e);
    }
    try {
      c.addAll(c.size(), c2);
    } catch (Exception e) {
      System.out.println("addAll(): " + e);
    }
    try {
      c.remove(0);
    } catch (Exception e) {
      System.out.println("remove(): " + e);
    }
    try {
      c.set(0, "X");
    } catch(Exception e) {
      System.out.println("set(): " + e);
    }
  }

  public static void main(String[] args) {
    List<String> list =
            Arrays.asList("A B C D E F G H I J K L".split(" "));
    test("Modifiable Copy", new ArrayList<String>(list));
    test("Arrays.asList()", list);
    test("unmodifiableList()",
            Collections.unmodifiableList(
                    new ArrayList<String>(list)));
  }
}