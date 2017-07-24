//: holding/UniqueWordsAlphabetic.java
package holding; /* Added by Eclipse.py */


/* Exercise 11.21
 Using a Map<String,Integer>, follow the form of
 UniqueWords.java to create a program that counts the occurrence of words in a file. Sort
 the results using Collections.sort( ) with a second argument of
 String.CASE_INSENSITIVE_ORDER (to produce an alphabetic sort), and display the
 result.
*/

import net.mindview.util.TextFile;

import java.util.*;

public class Exercise1121 {
  public static void main(String[] args) {
    List<String> words = new TextFile("tmp/SetOperations.java", "\\W+");

    Map<String, Integer> occurrence = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    for (String word : words) {
      Integer oc = occurrence.get(word);
      occurrence.put(word, oc == null ? 1 : oc + 1);
    }

    System.out.println(occurrence);
  }
}