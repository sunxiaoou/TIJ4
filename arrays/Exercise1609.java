//: initialization/BananaPeel.java
package arrays; /* Added by Eclipse.py */

/* Exercise 16.09:
 (3) Create the classes necessary for the Peel<Banana> example and show
 that the compiler doesn’t accept it. Fix the problem using an ArrayList.
 */


import java.util.*;

import static net.mindview.util.Print.*;

class Banana {}

class Peel<T> {
  void peel(T t) {
      print("peel " + t);
  }
}

public class Exercise1609 {
  public static void main(String[] args) {
    // Peel<Banana>[] peels = new Peel<Banana> [10];  // Illegal
    List<Peel<Banana>> peels = new ArrayList<>(10);
    for (int i = 0; i < 4; i ++)
      peels.add(new Peel<>());
    for (Peel<Banana> p : peels) {
      p.peel(new Banana());
      p.peel(new Banana());
      print();
    }
  }
}