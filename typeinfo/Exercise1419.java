//: typeinfo/toys/ToyTest.java
// Testing class Class.
package typeinfo;

/* Exercise 14.19
(4) In ToyTest.java, use reflection to create a Toy object using the non-
default constructor.
 */


import java.lang.reflect.*;
import java.util.regex.*;
import static net.mindview.util.Print.*;

class Toy {
  int i;
  public Toy() {}
  public Toy(int i) { this.i = i; }
}

public class Exercise1419 {
  private static Pattern p = Pattern.compile("\\w+\\.");
  public static void main(String args[]) {
    Object obj;
    Constructor[] ctors = Toy.class.getConstructors();
    for(Constructor ctor : ctors) {
      if (ctor.getParameterCount() != 0) {
        print(p.matcher(ctor.toString()).replaceAll(""));
        try {
          obj = ctor.newInstance(10);
          print(((Toy)obj).i);
        } catch (InstantiationException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    }
  }
}