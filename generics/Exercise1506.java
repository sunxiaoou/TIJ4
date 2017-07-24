//: generics/RandomList.java
package generics; /* Added by Eclipse.py */

/* Exercise 1506
 (1) Use RandomList with two more types in addition to the one shown in
 main( ).
 */

import generics.coffee.*;

public class Exercise1506 {
  public static void main(String[] args) {
    RandomList<Integer> ri = new RandomList<>();
    for(int i: new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
      ri.add(i);
    for(int i = 0; i < 10; i++)
      System.out.print(ri.select() + " ");

    RandomList<Coffee> rc = new RandomList<>();
    for(Coffee i: new Coffee[] {new Americano(), new Breve(), new Cappuccino(), new Latte(), new Mocha()})
      rc.add(i);

    System.out.println();
    for(int i = 0; i < 5; i++)
      System.out.print(rc.select() + " ");
  }
}