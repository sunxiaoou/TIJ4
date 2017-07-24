//: typeinfo/SweetShop.java
package typeinfo; /* Added by Eclipse.py */
// Examination of the way the class loader works.

/* Exercise14.07
 (3) Modify SweetShop.java so that each type of object creation is
 controlled by a command-line argument. That is, if your command line is "Java Sweetshop
 Candy," then only the Candy object is created. Notice how you can control which Class
 objects are loaded via the commandline argument.
*/

import static net.mindview.util.Print.print;

public class Exercise1407 {
  public static void main(String[] args) {
    if (args.length < 1) {
      System.out.println("Usage: java Exercise1407 classname");
      System.exit(0);
    }

    try {
      Object o = Class.forName(args[0]).newInstance();
      print(o);
    } catch (ClassNotFoundException e) {
      print("Couldn't find" + args[0]);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }
}