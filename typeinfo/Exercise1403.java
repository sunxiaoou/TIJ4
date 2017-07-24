//: typeinfo/Shapes.java
package typeinfo; /* Added by Eclipse.py */


/* Exercise 14.03
 (2) Add Rhomboid to Shapes.java. Create a Rhomboid, upcast it to a
 Shape, then downcast it back to a Rhomboid. Try downcasting to a Circle and see what
 happens.
 */

/* Exercise 14.04
 (2) Modify the previous exercise so that it uses instanceof to check the type
 before performing the downcast.
 */


class Rhomboid extends Shape {
  public String toString() { return "Rhomboid"; }
}

public class Exercise1403 {
  public static void main(String[] args) {
    Shape shape = new Rhomboid();
    shape.draw();

    System.out.println("Is a Circle: " + (shape instanceof Circle));
    try {
      ((Circle)shape).draw();
    } catch(Exception e) {
      System.out.println(e);
    }
  }
}