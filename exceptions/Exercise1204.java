//: exceptions/FullConstructors.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.04
 Create your own exception class using the extends keyword. Write a
 constructor for this class that takes a String argument and stores it inside the object with a
 String reference. Write a method that displays the stored String. Create a try-catch clause
 to exercise your new exception.
 */

class ExceptionM extends Exception {
  private String msg;
  ExceptionM(String msg) { this.msg = msg; }
  public String msg() { return msg; }
}

public class Exercise1204 {
  public static void main(String[] args) {
    try {
      throw new ExceptionM("A exception occurs");
    } catch (ExceptionM e) {
      System.out.println(e.msg());
      e.printStackTrace(System.out);
    }
  }
}