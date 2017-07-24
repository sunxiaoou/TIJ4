//: containers/CollectionDataGeneration.java
package containers; /* Added by Eclipse.py */
// Using the Generators defined in the Arrays chapter.

/* Exercise 17.10
 (7) Using a LinkedList as your underlying implementation, define your
 own SortedSet.
*/


import java.util.*;

import static net.mindview.util.Print.print;

class MySortSet<E extends Comparable<E>> extends LinkedList<E> {
  public boolean add(E e) {
    int i;
    if (contains(e))
      return false;
    for (i = 0; i < size(); i ++) {
      if (e.compareTo(get(i)) < 0)
        break;
    }
    add(i, e);
    return true;
  }

  public E first() { return get(0); }
  public E last() { return get(size() - 1); }

  private MySortSet<E> subSet(int from, int to) {
    MySortSet<E> subset = new MySortSet<>();
    for (int i = from; i <= to; i ++)
      subset.add(get(i));
    return subset;
  }

  public MySortSet<E> subSet(E from, E to) { return subSet(indexOf(from), indexOf(to) - 1); }
  public MySortSet<E> headSet(E to) { return subSet(0, indexOf(to) - 1); }
  public MySortSet<E> tailSet(E from) { return subSet(indexOf(from), size() - 1); }
}

public class Exercise1710 {
  public static void main(String[] args) {
    MySortSet<String> sortedSet = new MySortSet<String>();
    Collections.addAll(sortedSet, "one two three four five six seven eight".split(" "));
    print(sortedSet);
    String low = sortedSet.first();
    String high = sortedSet.last();
    print(low);
    print(high);
    Iterator<String> it = sortedSet.iterator();
    for(int i = 0; i <= 7; i++) {
      if(i == 3) low = it.next();
      else if(i == 7) high = it.next();
      else it.next();
    }
    print(low);
    print(high);
    print(sortedSet.subSet(low, high));
    print(sortedSet.headSet(high));
    print(sortedSet.tailSet(low));
  }
}