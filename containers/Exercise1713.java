//: holding/UniqueWordsAlphabetic.java
package containers; /* Added by Eclipse.py */


/* Exercise 17.13
 (4) Use AssociativeArray Java to create a wordoccurrence counter,
 mapping String to Integer. Using the net.mindview.util.TextFile utility in this book,
 open a text file and break up the words in that file using whitespace and punctuation, and
 count the occurrence of the words in that file.
*/

import net.mindview.util.TextFile;

import java.util.*;

import static net.mindview.util.Print.print;

class AssociativeArray2<K, V> extends AssociativeArray<K, V> {
  public AssociativeArray2(int length) { super(length); }
  public void put(K key, V value) {
    int i;
    if(index >= pairs.length)
      throw new ArrayIndexOutOfBoundsException();
    for (i = 0; i < index; i ++)
      if(key.equals(pairs[i][0])) {
        pairs[i][1] = value;
        break;
      }
    if (i == index)
      pairs[index++] = new Object[]{ key, value };
  }
}

public class Exercise1713 {
  public static void main(String[] args) {
    List<String> words = new TextFile("tmp/SetOperations.java", "\\W+");
    // Map<String, Integer> counts = new HashMap<>();
    AssociativeArray2<String, Integer> counts = new AssociativeArray2<>(60);

    for (String word : words) {
      Integer count = counts.get(word);
      count = (count == null) ? 1 : count + 1;
      try {
        counts.put(word, count);
      } catch(ArrayIndexOutOfBoundsException e) {
        print("Too many objects!");
        break;
      }
    }

    print(counts);
  }
}