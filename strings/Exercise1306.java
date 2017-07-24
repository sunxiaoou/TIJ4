//: strings/Conversion.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.06
 (2) Create a class that contains int, long, float and double fields. Create a
 toString( ) method for this class that uses String.format( ), and demonstrate that your
 class works correctly.
 */


public class Exercise1306 {
  private int i;
  private long l;
  private float f;
  private double d;

  Exercise1306(int i, long l, float f, double d) {
    this.i = i;
    this.l = l;
    this.f = f;
    this.d = d;
  }

  public String toString() {
    return String.format("i(%d)l(%d)f(%f)d(%e)", i, l, f, d);
  }

  public static void main(String[] args) {
    Exercise1306 ex = new Exercise1306(121, 500000000, 179, 179.543);
    System.out.println(ex);
  }
}