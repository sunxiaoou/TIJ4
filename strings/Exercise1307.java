//: strings/IntegerMatch.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.07
 (5) Using the documentation for java.util.regex.Pattern as a resource,
 write and test a regular expression that checks a sentence to see that it begins with a capital
 letter and ends with a period.
 */

public class Exercise1307 {
  static String regex = "^[A-Z].*\\.$";
  public static void main(String[] args) {
    System.out.println("Using".matches(regex));
    System.out.println("ends with a period.".matches(regex));
    System.out.println("Using a sentence ends with a period.".matches(regex));
  }
}
