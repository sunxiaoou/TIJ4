//: net/mindview/util/CountingGenerator.java
// Simple generator implementations.
package arrays;

/* Exercise 16.15
 (2) Modify ContainerComparison.java by creating a Generator for
 BerylliumSphere, and change main( ) to use that Generator with Generated.array().
 */


import java.util.Arrays;
import net.mindview.util.*;

import static net.mindview.util.Print.*;

class BerylliumSphereGenerator implements Generator<BerylliumSphere> {
  public BerylliumSphere next() {
    return new BerylliumSphere();
  }
}

public class Exercise1615 {
  public static void main(String args[]) {
    BerylliumSphere spheres[] = Generated.array(BerylliumSphere.class,
            new BerylliumSphereGenerator(), 5);
    print(Arrays.toString(spheres));
  }
}
