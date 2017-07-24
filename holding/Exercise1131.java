//: polymorphism/shape/RandomShapeGenerator.java
// A "factory" that randomly creates shapes.
package holding;

/* Exercise 11.31
 Modify polymorphism/shape/RandomShapeGenerator.java to
 make it Iterable. You’ll need to add a constructor that takes the number of elements that
 you want the iterator to produce before stopping. Verify that it works.
*/


import polymorphism.shape.*;
import java.util.*;

class RandomShapeGenerator implements Iterable<Shape> {
  private Shape shapes[];

  RandomShapeGenerator(int number) {
    shapes = new Shape[number];
    Random rand = new Random(47);
    for (int i = 0; i < number; i++) {
      switch (rand.nextInt(3)) {
        default:
          break;
        case 0:
          shapes[i] = new Circle();
          break;
        case 1:
          shapes[i] = new Square();
          break;
        case 2:
          shapes[i] = new Triangle();
          break;
      }
    }
  }

  public Iterator<Shape> iterator() {
    return new Iterator<Shape>() {
      private int index = 0;

      public boolean hasNext() {
        return index < shapes.length;
      }

      public Shape next() {
        return shapes[index++];
      }

      public void remove() { // Not implemented
        throw new UnsupportedOperationException();
      }
    };
  }
}

public class Exercise1131 {
  private static RandomShapeGenerator gen = new RandomShapeGenerator(10);

  public static void main(String[] args) {
    for (Shape shp : gen)
      shp.draw();
  }
}