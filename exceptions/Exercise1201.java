//: exceptions/InheritingExceptions.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.01
 Create a class with a main( ) that throws an object of class Exception
 inside a try block. Give the constructor for Exception a String argument. Catch the
 exception inside a catch clause and print the String argument. Add a finally clause and
 print a message to prove you were there.
 */

public class Exercise1201 {
  public void f() throws MyException {
    System.out.println("Throw MyException from f()");
    throw new MyException("Originated in f()");
  }
  public static void main(String[] args) {
    Exercise1201 sed = new Exercise1201();
    try {
      sed.f();
    } catch(MyException e) {
      System.out.println("Caught it (" + e.getMessage() + ")");
    } finally {
      System.out.println("Finally");
    }
  }
}