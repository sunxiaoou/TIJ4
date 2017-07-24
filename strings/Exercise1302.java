//: strings/InfiniteRecursion.java
package strings; /* Added by Eclipse.py */
// Accidental recursion.
// {RunByHand}

/* Exercise 13.02
 (1) Repair InfiniteRecursion.java.
 */

import java.util.ArrayList;
import java.util.List;

public class Exercise1302 {
  public String toString()
  {
    // return " InfiniteRecursion address: " + this + "\n";
    return " InfiniteRecursion address: " + super.toString() + "\n";
  }
  public static void main(String[] args) {
    List<Exercise1302> v =
      new ArrayList<Exercise1302>();
    for(int i = 0; i < 10; i++)
      v.add(new Exercise1302());
    System.out.println(v);
  }
} ///:~
