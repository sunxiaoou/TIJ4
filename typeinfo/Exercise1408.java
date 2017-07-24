//: typeinfo/SweetShop.java
package typeinfo; /* Added by Eclipse.py */
// Examination of the way the class loader works.

/* Exercise14.08
(5) Write a method that takes an object and recursively prints all the classes
in that object’s hierarchy.
*/

/* Exercise14.09
(5) Modify the previous exercise so that it uses
Class.getDeclaredFields( ) to also display information about the fields in a class.
*/

/* Exercise14.10
(3) Write a program to determine whether an array of char is a primitive
type or a true Object.
*/


import java.util.Arrays;

import static net.mindview.util.Print.print;

public class Exercise1408 {
  static void printInfo(Class cc) {
    if (cc == null)
      return;
    printInfo(cc.getSuperclass());
    print("Canonical name : " + cc.getCanonicalName());
    print("Field(s) : " + Arrays.toString(cc.getDeclaredFields()));
  }

  public static void main(String[] args) {
    printInfo((new Square2()).getClass());
    print();
    printInfo((new char[10]).getClass());
  }
}