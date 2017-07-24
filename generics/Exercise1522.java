package generics;

/* Exercise 15.22
 (6) Use a type tag along with reflection to create a method that uses the
 argument version of newInstance( ) to create an object of a class with a constructor that
 has arguments.
*/


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static net.mindview.util.Print.*;

class ConstructorHasArgs {
    private int i;
    public ConstructorHasArgs(Integer i) { this.i = i; }
    public String toString() { return "ConstructorHasArgs(" + i + ")"; }
}

public class Exercise1522 {
    public static void main(String args[]) {
        try {
            for (Constructor c : ConstructorHasArgs.class.getConstructors())
                print(c);
            ConstructorHasArgs a = ConstructorHasArgs.class.getConstructor(Integer.class).newInstance(10);
            print(a);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}