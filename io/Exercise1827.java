//: io/Worm.java
package io; /* Added by Eclipse.py */
// Demonstrates object serialization.

/* Exercise 18.27
 (1) Create a Serializable class containing a reference to an object of a
 second Serializable class. Create an instance of your class, serialize it to disk, then restore it
 and verify that the process worked correctly.
 */


import java.io.*;

import static net.mindview.util.Print.print;

class A implements Serializable {}
class B implements Serializable {
  A a = new A();
  @Override
  public String toString() {
    return super.toString() + "(" + a.toString() + ")";
  }
}

public class Exercise1827 {
  public static void main(String[] args) throws ClassNotFoundException, IOException {
    B b = new B();
    print("b = " + b);
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tmp/B.out"));
    out.writeObject("B storage\n");
    out.writeObject(b);
    out.close(); // Also flushes output
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("tmp/B.out"));
    String s = (String) in.readObject();
    B b2 = (B) in.readObject();
    print(s + "b2 = " + b2);
  }
}



