//: exceptions/InheritingExceptions.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.02
 Define an object reference and initialize it to null. Try to call a method
 through this reference. Now wrap the code in a try-catch clause to catch the exception.
 */
/* Exercise 12.08
 Write a class with a method that throws an exception of the type created
 in Exercise 2. Try compiling it without an exception specification to see what the compiler
 says.
 */


public class Exercise1202 {
  // public void f() /* throws MyException */ { // compile says: must be caught or declared to be thrown
  public void f() throws MyException {
    System.out.println("Throw MyException from f()");
    throw new MyException("Originated in f()");
  }
  public static void main(String[] args) {
    // Exercise1202 sed = new Exercise1202();
    Exercise1202 sed = null;
    try {
      sed.f();
    } catch(MyException e) {
      System.out.println("Caught it (" + e.getMessage() + ")");
    } catch(NullPointerException e) {
      System.out.println("Caught NullPointerException");
      e.printStackTrace(System.out);
    } finally {
      System.out.println("Finally");
    }
    System.out.println("end!");
  }
}