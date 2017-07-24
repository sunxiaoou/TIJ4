//: arrays/TestGenerated.java
package arrays; /* Added by Eclipse.py */

/* Exercise 16.13
 (2) Fill a String using CountingGenerator.Character.
 */

import net.mindview.util.CountingGenerator;
import static net.mindview.util.Print.*;


public class Exercise1613 {
  static String fill(int length) {
    CountingGenerator.Character cgc = new CountingGenerator.Character();
    char[] buf = new char[length];
    for (int i = 0; i < length; i++)
      buf[i] = cgc.next();
    return new String(buf);
  }

  public static void main(String[] args) {
    print(fill(10));
  }
}