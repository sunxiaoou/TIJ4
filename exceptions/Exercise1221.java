package exceptions;

/* Exercise 12.21
 Demonstrate that a derived-class constructor cannot catch exceptions
 thrown by its base-class constructor.
 */

import static net.mindview.util.Print.print;

class Base {
  Base() throws Exception01 {
    print("Base()");
    throw new Exception01();
  }
}

class Extend extends Base {
  Extend() throws Exception01 {
    print("Extend");
  }
}

public class Exercise1221 {
  public static void main(String[] args) {
    try {
      Extend extend = new Extend();
    } catch (Exception01 exception01) {
      exception01.printStackTrace();
    }
  }
}

