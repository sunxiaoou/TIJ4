//: enumerated/cartoons/EnumImplementation.java
// An enum can implement an interface
package enumerated;

/* Exercise 19.02
 (2) Change cartoons/EnumImplementation.java,
 instead of implementing an interface, make next( ) a static method.
 What are the benefits and drawbacks of this approach?
*/

import java.util.Random;

import static net.mindview.util.Print.*;

enum CartoonCharacter {
  SLAPPY, SPANKY, PUNCHY, SILLY, BOUNCY, NUTTY, BOB;
  private static Random rand = new Random(47);
  static CartoonCharacter next() {
    return values()[rand.nextInt(values().length)];
  }
}

public class Exercise1902 {
  public static void main(String[] args) {
    // Choose any instance:
    // CartoonCharacter cc = CartoonCharacter.BOB;
    for(int i = 0; i < 10; i ++)
      printnb(CartoonCharacter.next() + ", ");
    print();
  }
} /* Output:
BOB, PUNCHY, BOB, SPANKY, NUTTY, PUNCHY, SLAPPY, NUTTY, NUTTY, SLAPPY,
*///:~
