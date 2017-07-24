//: net/mindview/util/TextFile.java
// Static functions for reading and writing text files as
// a single string, and treating a file as an ArrayList.
package io;

/* Exercise 18.17
 (4) Using TextFile and a Map<Character,Integer>, create a program
 that counts the occurrence of all the different characters in a file. (So if there are 12
 occurrences of the letter ‘a’ in the file, the Integer associated with the Character
 containing ‘a’ in the Map contains ‘12’).
 */

import net.mindview.util.*;
import java.util.*;
import static net.mindview.util.Print.print;

public class Exercise1817 extends ArrayList<String> {
  public static void main(String[] args) {
    String file = TextFile.read("tmp/TextFile.java");
    Map<Character, Integer> counter = new TreeMap<>();
    for (int i = 0; i < file.length(); i ++) {
      Character ch = file.charAt(i);
      Integer num = counter.get(ch);
      counter.put(ch, num == null ? 1 : num + 1);
    }
    print(counter);
  }
}