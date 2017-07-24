//: generics/Generators.java
package generics; /* Added by Eclipse.py */
// A utility to use with Generators.

/* Exercise 15.13
 (4) Overload the fill( ) method so that the arguments and return types are
 the specific subtypes of Collection: List, Queue and Set. This way, you don’t lose the type
 of container. Can you overload to distinguish between List and LinkedList?
 */

import generics.coffee.Coffee;
import generics.coffee.CoffeeGenerator;
import net.mindview.util.Generator;

import java.util.*;

class Generators2 {
  public static <T> List<T> fill(List<T> coll, Generator<T> gen, int n) {
    for (int i = 0; i < n; i++)
      coll.add(gen.next());
    return coll;
  }

  public static <T> Set<T> fill(Set<T> coll, Generator<T> gen, int n) {
    for (int i = 0; i < n; i++)
      coll.add(gen.next());
    return coll;
  }
}

public class Exercise1513 {
  public static void main(String[] args) {
    Collection<Coffee> coffee = Generators2.fill(new ArrayList<>(), new CoffeeGenerator(), 5);
    for(Coffee c : coffee)
      System.out.println(c);

    Collection<Integer> fnumbers = Generators2.fill(new ArrayList<>(), new Fibonacci(), 12);
    for(int i : fnumbers)
      System.out.print(i + ", ");

    System.out.println();
    fnumbers = Generators2.fill(new TreeSet<>(), new Fibonacci(), 12);
    for(int i : fnumbers)
      System.out.print(i + ", ");
  }
}