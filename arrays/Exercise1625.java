//: holding/ListFeatures.java
package arrays; /* Added by Eclipse.py */

/* Exercise 16.25
 (3) Rewrite PythonLists.py in Java.
 */


import java.util.*;

import static net.mindview.util.Print.print;

class MyList<Interger> extends ArrayList<Integer> {
  MyList(List<Integer> self) { super(self); }
  List<Integer> getReversed() {
    List<Integer> reversed = new ArrayList<>();
    for (int i = size() - 1; i >= 0; i --)
      reversed.add(get(i));
    return reversed;
  }
}

public class Exercise1625 {
  public static void main(String[] args) {
    List<Integer> aList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    print(aList.getClass().getSimpleName());
    print(aList);
    print(aList.get(4));
    aList.add(6);
    aList.addAll(new ArrayList<>(Arrays.asList(7, 8)));
    print(aList);
    List<Integer> aSlice = aList.subList(2, 4);
    print(aSlice);
    MyList list2 = new MyList(aList);
    print(list2.getClass().getSimpleName());
    print(list2.getReversed());
  }
}