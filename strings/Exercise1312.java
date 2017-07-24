//: strings/Groups.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.12:
 (5) Modify Groups.java to count all of the unique words that do not start
 with a capital letter.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

public class Exercise1312 {
  static public final String POEM =
    "Twas brillig, and the slithy toves\n" +
    "Did gyre and gimble in the wabe.\n" +
    "All mimsy were the borogoves,\n" +
    "And the mome raths outgrabe.\n\n" +
    "Beware the Jabberwock, my son,\n" +
    "The jaws that bite, the claws that catch.\n" +
    "Beware the Jubjub bird, and shun\n" +
    "The frumious Bandersnatch.";
  public static void main(String[] args) {
    Matcher m = Pattern.compile("\\b[a-z]\\w+").matcher(POEM);
    int i;
    for (i = 0; m.find(); i ++) {
      printnb(m.group() + " ");
    }
    print();
    print("num = " + i);
  }
}