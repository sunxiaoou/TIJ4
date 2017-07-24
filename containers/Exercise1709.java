//: containers/CollectionDataGeneration.java
package containers; /* Added by Eclipse.py */
// Using the Generators defined in the Arrays chapter.

/* Exercise 17.09
 (2) Use RandomGenerator.String to fill a TreeSet, but use alphabetic
  ordering. Print the TreeSet to verify the sort order.
*/


import net.mindview.util.CollectionData;
import net.mindview.util.RandomGenerator;

import java.util.*;

import static net.mindview.util.Print.*;

public class Exercise1709 {
  public static void main(String[] args) {
    Set<String> set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    set.addAll(CollectionData.list(new RandomGenerator.String(9), 10));
    print(set);
  }
}
