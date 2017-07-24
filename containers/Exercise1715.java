//: holding/UniqueWordsAlphabetic.java
package containers; /* Added by Eclipse.py */


/* Exercise 17.15
 (1) Repeat Exercise 13 using a SlowMap.
*/


import net.mindview.util.TextFile;

import java.util.*;

import static net.mindview.util.Print.print;

public class Exercise1715 {
  private static List<String> words = new TextFile("tmp/SetOperations.java", "\\W+");

  static void count(Map<String, Integer> map) {
    print(map.getClass().getSimpleName());
    for (String word : words) {
      Integer count = map.get(word);
      count = (count == null) ? 1 : count + 1;
      map.put(word, count);
    }
    print(map);
  }

  public static void main(String[] args) {
    count(new HashMap<>());
    count(new SlowMap<>());
    count(new SlowMap2<>());
  }
}