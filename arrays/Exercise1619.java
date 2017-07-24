//: net/mindview/util/CountingGenerator.java
// Simple generator implementations.
package arrays;

/* Exercise 16.19
 (2) Create a class with an int field that’s initialized from a constructor
 argument. Create two arrays of these objects, using identical initialization values for each
 array, and show that Arrays.equals( ) says that they are unequal. Add an equals( )
 method to your class to fix the problem.
 */


import java.lang.reflect.Array;
import java.util.Arrays;

import static net.mindview.util.Print.*;

class AnInt {
    int i;
    public AnInt(Integer i) { this.i = i; }

    @Override
    public String toString() { return "" + i; }
}

class AnInt2 extends AnInt {
    public AnInt2(Integer i) { super(i); }

    @Override
    public boolean equals(Object obj) {
        return i == ((AnInt)obj).i;
    }
}

public class Exercise1619 {
    static <T extends AnInt> void compare(Class<T> type)  {
        T[] a = (T[])Array.newInstance(type, 5);
        T[] b = (T[])Array.newInstance(type, 5);
        try {
            Arrays.fill(a, type.getConstructor(Integer.class).newInstance(10));
            Arrays.fill(b, type.getConstructor(Integer.class).newInstance(10));
        } catch (Exception e) {
            e.printStackTrace();
        }
        print(Arrays.equals(a, b));
    }
    public static void main(String args[]) {
        compare(AnInt.class);
        compare(AnInt2.class);
    }
}