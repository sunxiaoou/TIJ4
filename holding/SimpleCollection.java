//: holding/SimpleCollection.java
package holding; /* Added by Eclipse.py */
import java.util.*;

public class SimpleCollection {
  public static void main(String[] args) {
    // Collection<Integer> c = new ArrayList<Integer>();
    // Exercise 1102: use a Set as a Collection
    Collection<Integer> c = new HashSet<>();

    for(int i = 0; i < 10; i++)
      c.add(i); // Autoboxing
    for(Integer i : c)
      System.out.print(i + ", ");

    c.add(0);
  }
} /* Output:
0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
*///:~
