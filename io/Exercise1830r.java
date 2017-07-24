//: io/RecoverCADState.java
package io; /* Added by Eclipse.py */
// Restoring the state of the pretend CAD system.
// {RunFirst: StoreCADState}
import java.io.*;
import java.util.*;

public class Exercise1830r {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) throws Exception {
    ObjectInputStream in = new ObjectInputStream(new FileInputStream("CADState2.out"));
    // Read in the same order they were written:
    Circle2.deserializeStaticState(in);
    Square2.deserializeStaticState(in);
    Line2.deserializeStaticState(in);
    List<Shape2> shapes = (List<Shape2>)in.readObject();
    System.out.println(shapes);
  }
}