//: typeinfo/SimpleDynamicProxy.java
package typeinfo; /* Added by Eclipse.py */

/* Exercise 14.22
(3) Modify SimpleDynamicProxy.java so that it measures method-call
times.
 */

import java.lang.reflect.*;
import java.util.*;

import static net.mindview.util.Print.print;

class MeasureCall2 extends LinkedHashMap<Method, Integer> implements InvocationHandler {
  private Object proxied;
  public MeasureCall2(Object proxied) {
    this.proxied = proxied;
  }
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    print("**** proxy: " + proxy.getClass() + ", method: " + method + ", args: " + args);
    if(args != null)
      for(Object arg : args)
        System.out.println("  " + arg);

    Object o = method.invoke(proxied, args);

    Integer quantity = get(method);
    put(method, quantity == null ? 1 : quantity + 1);

    return o;
  }
}

class Exercise1422 {
  public static void consumer(Interface iface) {
    iface.doSomething();
    iface.somethingElse("bonobo");
    iface.somethingElse("foo");
  }
  public static void main(String[] args) {
    RealObject real = new RealObject();
    consumer(real);
    // Insert a proxy and call again:
    MeasureCall2 m2 = new MeasureCall2(real);
    Interface proxy = (Interface)Proxy.newProxyInstance(
      Interface.class.getClassLoader(), new Class[]{ Interface.class }, m2);
    consumer(proxy);
    print(m2);
  }
}