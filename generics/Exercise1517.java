package generics; /* Added by Eclipse.py */


/* Exercise 15.17
 (4) Study the JDK documentation for EnumSet. You’ll see that there’s a
 clone( ) method defined. However, you cannot clone( ) from the reference to the Set
 interface passed in Sets.java. Can you modify Sets.java to handle both the general case of a
 Set interface as shown, and the special case of an EnumSet, using clone( ) instead of
 creating a new HashSet?
*/


import generics.watercolors.*;
import java.util.*;
import static net.mindview.util.Print.*;
import static generics.watercolors.Watercolors.*;


class Sets {
    private static <T> Set<T> clone(Set<T> a) {
        if (a instanceof EnumSet)
            return ((EnumSet)a).clone();
        else
            return new HashSet<T>(a);
    }
    static <T> Set<T> union(Set<T> a, Set<T> b) {
        //  Set<T> result = new HashSet<T>(a);
        Set<T> result = clone(a);
        result.addAll(b);
        return result;
    }
    static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        // Set<T> result = new HashSet<T>(a);
        Set<T> result = clone(a);
        result.retainAll(b);
        return result;
    }
    // Subtract subset from superset:
    static <T> Set<T> difference(Set<T> superset, Set<T> subset) {
        //  Set<T> result = new HashSet<T>(superset);
        Set<T> result = clone(superset);
        result.removeAll(subset);
        return result;
    }
    // Reflexive--everything not in the intersection:
    static <T> Set<T> complement(Set<T> a, Set<T> b) {
        return difference(union(a, b), intersection(a, b));
    }
}

public class Exercise1517 {
    public static void main(String[] args) {
        Set<Watercolors> set1 = EnumSet.range(BRILLIANT_RED, VIRIDIAN_HUE);
        Set<Watercolors> set2 = EnumSet.range(CERULEAN_BLUE_HUE, BURNT_UMBER);
        print("set1: " + set1);
        print("set2: " + set2);
        print("union(set1, set2): " + Sets.union(set1, set2));
        Set<Watercolors> subset = Sets.intersection(set1, set2);
        print("intersection(set1, set2): " + subset);
        print("difference(set1, subset): " + Sets.difference(set1, subset));
        print("difference(set2, subset): " + Sets.difference(set2, subset));
        print("complement(set1, set2): " + Sets.complement(set1, set2));
    }
}