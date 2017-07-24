//: strings/TestRegularExpression.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.11
 (2) Apply the regular expression
  (?i)((^[aeiou])|(\s+[aeiou]))\w+?[aeiou]\b
 to
  "Arline ate eight apples and one orange while Anita hadn’t any"
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.mindview.util.Print.print;

public class Exercise1311 {
  // private static String regex = "(?i)((^[aeiou])|(\\s+[aeiou]))\\w+?[aeiou]\\b";
  private static String regex = "(?i)\\b[aeiou]\\w+?[aeiou]\\b";

  public static void main(String[] args) {
    String string = "Arline ate eight apples and one orange while Anita hadn’t any";
    print("Input: \"" + string + "\"");
    print("Regular expression: \"" + regex + "\"");
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(string);
    while(m.find()) {
      print("Match \"" + m.group() + "\" at positions " +
        m.start() + "-" + (m.end() - 1));
    }
  }
}