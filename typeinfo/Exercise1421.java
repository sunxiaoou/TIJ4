//: typeinfo/SimpleProxyDemo.java
package typeinfo; /* Added by Eclipse.py */

/* Exercise 14.21
(3) Modify SimpleProxyDemo.java so that it measures method-call
times.
 */


import java.util.*;
import static net.mindview.util.Print.print;

class MeasureCall extends LinkedHashMap<String, Integer> implements Interface {
  private Interface proxied;
  public MeasureCall(Interface proxied) {
    this.proxied = proxied;
  }

  private void count(String m) {
    Integer quantity = get(m);
    put(m, quantity == null ? 1 : quantity + 1);
  }

  public void doSomething() {
    // print("SimpleProxy doSomething");
    proxied.doSomething();
    count("doSomething");
  }

  public void somethingElse(String arg) {
    // print("SimpleProxy somethingElse " + arg);
    proxied.somethingElse(arg);
    count("somethingElse");
  }
}

class Exercise1421 {
  public static void consumer(Interface iface) {
    iface.doSomething();
    iface.somethingElse("bonobo");
    iface.somethingElse("foofoo");
    print(iface);
  }
  public static void main(String[] args) {
    consumer(new RealObject());
    consumer(new MeasureCall(new RealObject()));
  }
}