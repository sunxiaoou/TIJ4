package containers;


/* Exercise 1728
 (4) Modify net/mindview/util/Tuple.java to make it a general-purpose
 class by adding hashCode( ), equals( ), and implementing Comparable for each type of
 Tuple.
*/


import java.util.*;

import static net.mindview.util.Print.*;

class TwoTuple<A extends Comparable, B extends Comparable> implements Comparable<TwoTuple> {
  public final A first;
  public final B second;
  TwoTuple(A a, B b) { first = a; second = b; }
  public String toString() {
    return "(" + first + ", " + second + ")";
  }
  public boolean equals(Object o) {
    return o instanceof TwoTuple && first.equals(((TwoTuple)o).first) && second.equals(((TwoTuple)o).second);
  }
  public int hashCode() {
    int result = 17;
    result = 37 * result + first.hashCode();
    result = 37 * result + second.hashCode();
    return result;
  }
  public int compareTo(TwoTuple arg) {
    int compare = first.compareTo(arg.first);
    if (compare != 0)
      return compare;
    return second.compareTo(arg.second);
  }
}

class ThreeTuple<A extends Comparable, B extends Comparable, C extends Comparable> extends TwoTuple<A, B> {
  public final C third;
  ThreeTuple(A a, B b, C c) {
    super(a, b);
    third = c;
  }
  public String toString() {
    return "(" + first + ", " + second + ", " + third +")";
  }
  public boolean equals(Object o) {
    return super.equals(o) && third.equals(((ThreeTuple)o).third);
  }
  public int hashCode() {
    return 37 * super.hashCode() + third.hashCode();
  }

  @Override   // here is tricky, it looks you must use TwoTuple here, otherwise compareTo() cannot be override.
  public int compareTo(TwoTuple arg) {
    int compare = super.compareTo(arg);
    if (compare != 0)
      return compare;
    return third.compareTo(((ThreeTuple)arg).third);
  }
}

public class Exercise1728 {
  public static void main(String[] args) {
    // Map<ThreeTuple<String, Integer, Integer>, Integer> map = new HashMap<>();
    Map<ThreeTuple<String, Integer, Integer>, Integer> map = new TreeMap<>();
    ThreeTuple<String, Integer, Integer>[] cs = new ThreeTuple[5];
    for (int i = 0; i < cs.length; i ++) {
      cs[i] = new ThreeTuple<>("hi", 0, i);
      map.put(cs[i], i); // Autobox int -> Integer
    }
    print(map);
    for (ThreeTuple tuple : cs) {
      print("Looking up " + tuple);
      print(map.get(tuple));
    }
  }
}