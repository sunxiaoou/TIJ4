//: net/mindview/util/CountingGenerator.java
// Simple generator implementations.
package arrays;

/* Exercise 16.18
 (3) Create and fill an array of BerylliumSphere. Copy this array to a new
 array and show that it’s a shallow copy.
 */


import net.mindview.util.Generated;

import java.util.Arrays;

import static net.mindview.util.Print.print;

public class Exercise1618 {
  public static void main(String args[]) {
    BerylliumSphere spheres[] = Generated.array(BerylliumSphere.class,
            new BerylliumSphereGenerator(), 5);

    BerylliumSphere s2[] = new BerylliumSphere[10];
    System.arraycopy(spheres, 0, s2, 0, spheres.length);
    print(Arrays.toString(s2));
  }
}
