//: exceptions/InheritingExceptions.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.10
 Create a class with two methods, f( ) and g( ). In g( ), throw an
 exception of a new type that you define. In f( ), call g( ), catch its exception and, in the catch
 clause, throw a different exception (of a second type that you define). Test your code in
 main( ).
 */

public class Exercise1210 {
  public static void g() throws Exception01 {
    System.out.println("Throw Exception01 from g()");
    throw new Exception01();
  }
  public static void f() throws Exception02 {
    try {
      g();
    } catch (Exception01 e) {
      e.printStackTrace(System.out);
      System.out.println("Throw Exception02 from f()");
      throw new Exception02();
    }
  }

  public static void main(String[] args) {
    try {
      f();
    } catch(Exception02 e) {
      e.printStackTrace(System.out);
    }
  }
}