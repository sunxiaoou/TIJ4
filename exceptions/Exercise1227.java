//: exceptions/InheritingExceptions.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.27
 Modify Exercise 3 to convert the exception to a RuntimeException.
 */

public class Exercise1227 {
  public static void main(String[] args) {
    Integer a[] = new Integer[5];
    try {
      for (int i = 0; i < 6; i ++)
        a[i] = i;
    } catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
}