//: typeinfo/PetCount4.java
package typeinfo; /* Added by Eclipse.py */

/* Exercise 14.12
(3) Use TypeCounter with the CoffeeGenerator.java class in the Generics
chapter.
 */


import net.mindview.util.TypeCounter;
import generics.coffee.*;

import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

public class Exercise1412 {
  public static void main(String[] args) {
    TypeCounter counter = new TypeCounter(generics.coffee.Coffee.class);
    for(generics.coffee.Coffee coffee : new CoffeeGenerator(20)) {
      printnb(coffee.getClass().getSimpleName() + " ");
      counter.count(coffee);
    }
    print();
    print(counter);
  }
}