//: containers/Lists.java
package containers; /* Added by Eclipse.py */
// Things you can do with Lists.

/* Exercise 17.07
 (4) Create both an ArrayList and a LinkedList, and fill each using the
 Countries.names( ) generator. Print each list using an ordinary Iterator, then insert one
 list into the other by using a Listlterator, inserting at every other location. Now perform the
 insertion starting at the end of the first list and moving backward.
 */


import net.mindview.util.Countries;

import java.util.*;

import static net.mindview.util.Print.print;
import static net.mindview.util.Print.printnb;

public class Exercise1707 {
  public static void main(String[] args) {
    List<String> aList = new ArrayList<>(Countries.names(10));
    List<String> lList = new LinkedList<>(Countries.names(10));

    for (String name : aList)
      printnb(name + ", ");
    print();

    Iterator<String> it = lList.iterator();
    while (it.hasNext())
      printnb(it.next() + ", ");
    print();

    ListIterator<String> ait = aList.listIterator(aList.size());
    ListIterator<String> lit = lList.listIterator(lList.size());
    while (ait.hasPrevious()) {
      lit.previous();
      lit.add(ait.previous());
      lit.previous();
    }
    // print(lList);

    lList = new LinkedList<>(Arrays.asList("one", "two"));
    print(lList);

    int a = lList.indexOf("two");
    print(a);
    lit = lList.listIterator(1);
    print(a == lList.size() - 1);
    print(lit.next());
    print(lit.hasNext());
  }
}
