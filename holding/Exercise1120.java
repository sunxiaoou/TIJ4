package holding; /* Added by Eclipse.py */

/* Exercise 11.16:
 Exercise 20: (3) Modify Exercise 16 so that you keep a count of the occurrence of each
 vowel.
*/

import net.mindview.util.TextFile;

import java.util.*;

public class Exercise1120 {
  public static void main(String[] args) {
    Set<Character> vowels = new TreeSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    Map<Character, Integer> occurrence = new HashMap<>();
    char array[] = TextFile.read("tmp/UniqueWords.java").toLowerCase().toCharArray();

    for (char c : array) {
      if (vowels.contains(c)) {
        Integer o = occurrence.get(c);
        occurrence.put(c, o == null ? 1 : o + 1);
      }
    }

    System.out.print(occurrence);
  }
}