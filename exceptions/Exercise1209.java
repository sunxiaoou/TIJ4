//: exceptions/FullConstructors.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.09
 Create three new types of exceptions. Write a class with a method that
 throws all three. In main( ), call the method but only use a single catch clause that will
 catch all three types of exceptions.
 */


class Exception01 extends Exception {}
class Exception02 extends Exception {}
class Exception03 extends Exception {}

public class Exercise1209 {
  public static void f(int i) throws Exception01, Exception02, Exception03 {
    System.out.println("Throw Exception" + i + " from f()");
    switch (i) {
      case 1:
        throw new Exception01();
      case 2:
        throw new Exception02();
      default:
        throw new Exception03();
    }
  }

  public static void main(String[] args) {
    for (int i = 0; i < 3; i ++)
      try {
        f(i);
      } catch(Exception e) {
        e.printStackTrace(System.out);
      }
    }

}