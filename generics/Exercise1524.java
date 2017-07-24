//: generics/ClassTypeCapture.java
package generics; /* Added by Eclipse.py */

/* Exercise 1524
 (3) Modify Exercise 21 so that factory objects are held in the Map instead
 of Class<?>.
 */


import java.util.*;

class ClassTypeCapture3 {
    private static Map<String, FactoryI> map = new HashMap<>();
    static void addType(String typename, FactoryI factory) { map.put(typename, factory); }
    static <T> T createNew(String typename) {
        return (T)map.get(typename).create();
    }
}

public class Exercise1524 {
    public static void main(String[] args) {
        ClassTypeCapture3.addType("Integer", new IntegerFactory());
        ClassTypeCapture3.addType("Widget", new Widget.Factory());
        Integer i = ClassTypeCapture3.createNew("Integer");
        Widget w = ClassTypeCapture3.createNew("Widget");
        System.out.println(i + ", " + w);
    }
}