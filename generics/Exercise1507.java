//: generics/IterableFibonacci.java
package generics; /* Added by Eclipse.py */
// Adapt the Fibonacci class to make it Iterable.

/* Exercise 15.07
 (2) Use composition instead of inheritance to adapt Fibonacci to make it
 Iterable.
 */

import java.util.Iterator;

class IterableFibonacci2 implements Iterable<Integer> {
  private int n;
  private Fibonacci fibonacci = new Fibonacci();

  IterableFibonacci2(int count) { n = count; }

  public Iterator<Integer> iterator() {
    return new Iterator<Integer>() {
      public boolean hasNext() { return n > 0; }
      public Integer next() {
        n--;
        return fibonacci.next();
      }
      public void remove() { // Not implemented
        throw new UnsupportedOperationException();
      }
    };
  }
}

public class Exercise1507 {
  public static void main(String[] args) {
    for(int i : new IterableFibonacci2(18))
      System.out.print(i + " ");
  }
} /* Output:
1 1 2 3 5 8 13 21 34 55 89 144 233 377 610 987 1597 2584
*///:~
