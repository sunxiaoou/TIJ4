package containers;

/* Exercise 17.01
 (1) Create a List (try both ArrayList and LinkedList) and fill it using
 Countries. Sort the list and print it, then apply Collections.shuffle( ) to the list
 repeatedly, printing it each time so that you can see how the shuffle( ) method randomizes
 the list differently each time
 */

import java.util.*;
import net.mindview.util.*;

import static net.mindview.util.Print.*;

public class Exercise1701 {
  static void printList(List list) {
    print(list.subList(0, 5));
    print(list.subList(list.size() - 5, list.size()));
    print("After shuffle");
    Collections.shuffle(list);
    print(list.subList(0, 5));
    print(list.subList(list.size() - 5, list.size()));
  }

  public static void main(String args[]) {
    List<TwoTuple<String, String>> countries = new ArrayList<>();
    for(int i = 0; i < Countries.DATA.length; i ++)
      countries.add(new TwoTuple<>(Countries.DATA[i][0], Countries.DATA[i][1]));
    printList(countries);
    print();
    countries = new LinkedList<>();
    for(int i = 0; i < Countries.DATA.length; i ++)
      countries.add(new TwoTuple<>(Countries.DATA[i][0], Countries.DATA[i][1]));
    printList(countries);
  }
}
