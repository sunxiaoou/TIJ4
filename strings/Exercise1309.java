//: strings/Replacing.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.09
 (4) Using the documentation for java.util.regex.Pattern as a resource,
 replace all the vowels in Splitting.knights with underscores.
*/

import static net.mindview.util.Print.print;

public class Exercise1309 {
  static String s = Splitting.knights;
  public static void main(String[] args) {
    print(s);
    print(s.replaceAll("[aAeEiIoOuU]", "_"));
  }
}
