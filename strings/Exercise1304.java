//: strings/Receipt.java
package strings; /* Added by Eclipse.py */

/* Exercise 13.04
 (3) Modify Receipt.java so that the widths are all controlled by a single set of
 constant values. The goal is to allow you to easily change a width by changing a single value
 in one place.
 */


import java.util.Formatter;

public class Exercise1304 {
  private final static int width[] = {15, 5, 10};
  //  "%-15s %5s %10s\n"
  private final static String format1 = "%-" + width[0] + "s %" + width[1] + "s %" + width[2] + "s\n";
  //  "%-15.15s %5d %10.2f\n"
  private final static String format2 = "%-" + width[0] + "." + width[0] + "s %" + width[1] + "d %" + width[2] + ".2f\n";
  //  "%-15s %5s %10.2f\n"
  private final static String format3 = "%-" + width[0] + "s %" + width[1] + "s %" + width[2] + ".2f\n";

  private double total = 0;
  private Formatter f = new Formatter(System.out);
  public void printTitle() {
    f.format(format1, "Item", "Qty", "Price");
    f.format(format1, "----", "---", "-----");
  }
  public void print(String name, int qty, double price) {
    f.format(format2, name, qty, price);
    total += price;
  }
  public void printTotal() {
    f.format(format3, "Tax", "", total*0.06);
    f.format(format1, "", "", "-----");
    f.format(format3, "Total", "",
      total * 1.06);
  }
  public static void main(String[] args) {
    Exercise1304 receipt = new Exercise1304();
    receipt.printTitle();
    receipt.print("Jack's Magic Beans", 4, 4.25);
    receipt.print("Princess Peas", 3, 5.1);
    receipt.print("Three Bears Porridge", 1, 14.29);
    receipt.printTotal();
  }
}