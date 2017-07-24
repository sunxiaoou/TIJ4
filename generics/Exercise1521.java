//: generics/ClassTypeCapture.java
package generics; /* Added by Eclipse.py */

/* Exercise 15.21
 (4) Modify ClassTypeCapture.java by adding a
 Map<String,Class<?>>, a method addType(String typename, Class<?> kind), and
 a method createNew(String typename). createNew( ) will either produce a new
 instance of the class associated with its argument string, or produce an error message.
 */


import java.util.*;

class ClassTypeCapture2 {
    private static Map<String, Class<?>> map = new HashMap<>();
    static void addType(String typename, Class<?> kind) { map.put(typename, kind); }
    static <T> T createNew(String typename) throws IllegalAccessException, InstantiationException {
        return (T)map.get(typename).newInstance();
    }
}

public class Exercise1521 {
    public static void main(String[] args) {
        ClassTypeCapture2.addType("Building", Building.class);
        ClassTypeCapture2.addType("House", House.class);
        try {
            Building b = ClassTypeCapture2.createNew("Building");
            Building h = ClassTypeCapture2.createNew("House");
            System.out.println(b + ", " + h);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}