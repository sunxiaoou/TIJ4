//: typeinfo/PetCount4.java
package typeinfo; /* Added by Eclipse.py */

/* Exercise 14.13
(3) Use TypeCounter with the RegisteredFactories.java example in this
chapter.
 */


import net.mindview.util.TypeCounter;

import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

public class Exercise1413 {
  public static void main(String[] args) {
    TypeCounter counter = new TypeCounter(Part.class);
    for(int i = 0; i < 20; i++) {
      Part part = Part.createRandom();
      printnb(part.getClass().getSimpleName() + " ");
      counter.count(part);
    }
    print();
    print(counter);
  }
}