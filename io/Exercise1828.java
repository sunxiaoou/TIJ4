//: io/Blips.java
package io; /* Added by Eclipse.py */
// Simple use of Externalizable & a pitfall.

/* Exercise 18.28
 (2) In Blips.java, copy the file and rename it to BlipCheck.java and
 rename the class BlipCheck to BlipCheck (making it public and removing the public scope
 from the class Blips in the process). Remove the //! marks in the file and execute the
 program, including the offending lines. Next, comment out the default constructor for
 BlipCheck. Run it and explain why it works. Note that after compiling, you must execute the
 program with "Java Blips" because the main( ) method is still in the class Blips.
 */

import java.io.*;

import static net.mindview.util.Print.print;

public class Exercise1828 implements Externalizable {
  // public Exercise1828() { print("BlipCheck Constructor"); }
  public void writeExternal(ObjectOutput out) throws IOException { print("BlipCheck.writeExternal"); }
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    print("BlipCheck.readExternal");
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    print("Constructing objects:");
    Blip1 b1 = new Blip1();
    Exercise1828 bc = new Exercise1828();
    ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("tmp/Blips.out"));
    print("Saving objects:");
    o.writeObject(b1);
    o.writeObject(bc);
    o.close();
    // Now get them back:
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("tmp/Blips.out"));
    print("Recovering b1:");
    b1 = (Blip1)in.readObject();
    print("Recovering bc:");
    bc = (Exercise1828)in.readObject();
  }
}