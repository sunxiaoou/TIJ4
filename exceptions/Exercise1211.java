//: exceptions/InheritingExceptions.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.11
 Repeat the previous exercise, but inside the catch clause, wrap g( )’s
 exception in a RuntimeException.
 */

public class Exercise1211 {
  public static void g() throws Exception01 {
    System.out.println("Throw Exception01 from g()");
    throw new Exception01();
  }
  public static void f() {
    try {
      g();
    } catch (Exception01 e) {
      e.printStackTrace(System.out);
      System.out.println("Throw RuntimeException from f1()");
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) {
    try {
      f();
    } catch(Exception e) {
      e.printStackTrace(System.out);
    }
  }
}