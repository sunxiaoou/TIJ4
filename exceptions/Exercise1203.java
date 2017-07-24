//: exceptions/InheritingExceptions.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.03
 Write code to generate and catch an ArrayIndexOutOfBoundsException.
 */

public class Exercise1203 {
  public static void main(String[] args) {
    Integer a[] = new Integer[5];
    try {
      for (int i = 0; i < 6; i ++)
        a[i] = i;
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("Caught ArrayIndexOutOfBoundsException");
    }
  }
}