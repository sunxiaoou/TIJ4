//: io/StoreCADState.java
package io; /* Added by Eclipse.py */
// Saving the state of a pretend CAD system.

/* Exercise 18.30:
 (1) Repair the program CADState.java as described in the text.
 1. Add a serializeStaticState( ) and deserializeStaticState( ) to the shapes.
 2. Remove the ArrayList shapeTypes and all code related to it.
 3. Add calls to the new serialize and deserialize static methods in the shapes.
 */


import java.io.*;
import java.util.*;

abstract class Shape2 implements Serializable {
  public static final int RED = 1, BLUE = 2, GREEN = 3;
  private int xPos, yPos, dimension;
  private static Random rand = new Random(47);
  private static int counter = 0;
  public abstract void setColor(int newColor);
  public abstract int getColor();
  public Shape2(int xVal, int yVal, int dim) {
    xPos = xVal;
    yPos = yVal;
    dimension = dim;
  }
  public String toString() {
    return getClass() +
      "color[" + getColor() + "] xPos[" + xPos +
      "] yPos[" + yPos + "] dim[" + dimension + "]\n";
  }
  public static Shape2 randomFactory() {
    int xVal = rand.nextInt(100);
    int yVal = rand.nextInt(100);
    int dim = rand.nextInt(100);
    switch(counter++ % 3) {
      default:
      case 0: return new Circle2(xVal, yVal, dim);
      case 1: return new Square2(xVal, yVal, dim);
      case 2: return new Line2(xVal, yVal, dim);
    }
  }
}

class Circle2 extends Shape2 {
  private static int color = RED;
  public static void serializeStaticState(ObjectOutputStream os) throws IOException { os.writeInt(color); }
  public static void deserializeStaticState(ObjectInputStream os) throws IOException { color = os.readInt(); }
  public Circle2(int xVal, int yVal, int dim) {
    super(xVal, yVal, dim);
  }
  public void setColor(int newColor) { color = newColor; }
  public int getColor() { return color; }
}

class Square2 extends Shape2 {
  private static int color;
  public static void serializeStaticState(ObjectOutputStream os) throws IOException { os.writeInt(color); }
  public static void deserializeStaticState(ObjectInputStream os) throws IOException { color = os.readInt(); }
  public Square2(int xVal, int yVal, int dim) {
    super(xVal, yVal, dim);
    color = RED;
  }
  public void setColor(int newColor) { color = newColor; }
  public int getColor() { return color; }
}

class Line2 extends Shape2 {
  private static int color = RED;
  public static void serializeStaticState(ObjectOutputStream os) throws IOException { os.writeInt(color); }
  public static void deserializeStaticState(ObjectInputStream os) throws IOException { color = os.readInt(); }
  public Line2(int xVal, int yVal, int dim) {
    super(xVal, yVal, dim);
  }
  public void setColor(int newColor) { color = newColor; }
  public int getColor() { return color; }
}

public class Exercise1830 {
  public static void main(String[] args) throws Exception {
    List<Shape2> shapes = new ArrayList<>();
    // Make some shapes:
    for(int i = 0; i < 10; i++)
      shapes.add(Shape2.randomFactory());
    // Set all the static colors to GREEN:
    for(int i = 0; i < 10; i++)
      (shapes.get(i)).setColor(Shape2.GREEN);
    // Save the state vector:
    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CADState2.out"));
    Circle2.serializeStaticState(out);
    Square2.serializeStaticState(out);
    Line2.serializeStaticState(out);
    out.writeObject(shapes);
    // Display the shapes:
    System.out.println(shapes);
  }
}
