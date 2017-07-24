//: typeinfo/Shapes.java
package typeinfo;

/* Exercise 14.05
 (3) Implement a rotate(Shape) method in Shapes.java, such that it
 checks to see if it is rotating a Circle (and, if so, doesn’t perform the operation).
 */

/* Exercise 14.06
 (4) Modify Shapes.java so that it can "highlight" (set a flag in) all shapes of
 a particular type. The toString( ) method for each derived Shape should indicate whether
 that Shape is "highlighted."
 */

import java.util.Arrays;
import java.util.List;

abstract class Shape2 {
    boolean highlight;
    void draw() { System.out.println(this + ".draw()"); }
    void rotate() { System.out.println(this + ".rotate()"); }
    abstract public String toString();
}

class Circle2 extends Shape2 {
    Circle2() { highlight = false; }
    public String toString() {
        return "Circle(" + highlight + ")";
    }
}

class Rectangle2 extends Shape2 {
    Rectangle2() { highlight = true; }
    public String toString() {
        return "Square(" + highlight + ")";
    }
}

class Square2 extends Rectangle2 {
    public String toString() {
        return "Square(" + highlight + ")";
    }
}

class Triangle2 extends Shape2 {
    Triangle2() { highlight = false; }
    public String toString() {
        return "Triangle(" + highlight + ")";
    }
}

public class Exercise1405 {
    public static void main(String[] args) {
        List<Shape2> shapeList = Arrays.asList(
            new Circle2(), new Square2(), new Triangle2()
        );
        for(Shape2 shape : shapeList) {
            shape.draw();
            if (!(shape instanceof Circle2))
                shape.rotate();
        }
    }
}