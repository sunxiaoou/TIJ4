//: strings/TestRegularExpression.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.10
 (2) For the phrase "Java now has regular expressions" evaluate whether the
 following expressions will find a match:
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.mindview.util.Print.print;

public class Exercise1310 {
  private static String regexs[] = {
    "^Java", "\\breg.*", "n.w\\s+h(a|i)s",
    "s?", "s*", "s+", "s{4}", "s{1}", "s{0,3}"
  };

  public static void main(String[] args) {
    String string = "Java now has regular expressions";
    print("Input: \"" + string + "\"");
    for(String arg : regexs) {
      print("Regular expression: \"" + arg + "\"");
      Pattern p = Pattern.compile(arg);
      Matcher m = p.matcher(string);
      while(m.find()) {
        print("Match \"" + m.group() + "\" at positions " +
          m.start() + "-" + (m.end() - 1));
      }
    }
  }
}