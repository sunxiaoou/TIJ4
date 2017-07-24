//: exceptions/Cleanup.java
package exceptions; /* Added by Eclipse.py */
// Guaranteeing proper cleanup of a resource.

class FailingConstructor {
  FailingConstructor() throws MyException {
    System.out.println("FailingConstructor()");
    throw new MyException();
  }
  void f() { System.out.println("f()"); }
}

public class Exercise1222 {
  public static void main(String[] args) {
    try {
      FailingConstructor fc = new FailingConstructor();
      try {
        fc.f();;
      } catch(Exception e) {
        System.out.println("Caught Exception in main");
        e.printStackTrace(System.out);
      } finally {
      }
    } catch(Exception e) {
      System.out.println("construction failed");
      e.printStackTrace();
    }
  }
}