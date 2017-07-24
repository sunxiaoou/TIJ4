//: strings/Conversion.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.20
 (2) Create a class that contains int, long, float and double and String
 fields. Create a constructor for this class that takes a single String argument, and scans that
 string into the various fields. Add a toString( ) method and demonstrate that your class
 works correctly.
 */


import java.util.Scanner;

public class Exercise1320 {
  private int i;
  private long l;
  private float f;
  private double d;
  private String s;

  Exercise1320(String input) {
    Scanner stdin = new Scanner(input);
    stdin.useDelimiter("\\s*,\\s*");
    this.i = stdin.nextInt();
    this.l = stdin.nextLong();
    this.f = stdin.nextFloat();
    this.d = stdin.nextDouble();
    this.s = stdin.next();
  }

  public String toString() {
    return String.format("i(%d)l(%d)f(%f)d(%e)s(%s)", i, l, f, d, s);
  }

  public static void main(String[] args) {
    Exercise1320 ex = new Exercise1320("121, 500000000, 179, 179.543, hello world");
    System.out.println(ex);
  }
}