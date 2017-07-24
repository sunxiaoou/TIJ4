//: holding/LinkedListFeatures.java
package holding; /* Added by Eclipse.py */

/* Exercise 11.14
 Create an empty LinkedList<Integer>. Using a Listlterator, add
 Integers to the List by always inserting them in the middle of the List.
*/

import java.util.LinkedList;
import static net.mindview.util.Print.print;

public class Exercise1114 {
  public static void main(String[] args) {
    LinkedList<Integer> pets = new LinkedList<>();

    for (int i = 0; i < 10; i++) {
      pets.add(pets.size() / 2, i);
      print(pets);
    }
  }
}