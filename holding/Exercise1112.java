//: holding/ListFeatures.java
package holding; /* Added by Eclipse.py */

import java.util.*;

import static net.mindview.util.Print.print;

/* Exercise 11.12
 Create and populate a List<Integer>. Create a second List<Integer>
 of the same size as the first, and use ListIterators to read elements from the first List and
 insert them into the second in reverse order. (You may want to explore a number of different
 ways to solve this problem.)
*/

public class Exercise1112 {
  public static void main(String[] args) {
    List<Integer> pets = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 3, 5));
    List<Integer> pets2 = new ArrayList<>(pets.size());

    ListIterator<Integer> it = pets.listIterator(pets.size());

    while(it.hasPrevious())
      pets2.add(it.previous());

    print(pets);
    print(pets2);
  }
}
