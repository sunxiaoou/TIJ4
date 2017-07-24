//: holding/UniqueWordsAlphabetic.java
package holding; /* Added by Eclipse.py */


/* Exercise 11.25
 Create a Map<String,ArrayList<Integer>>. Use
 net.mindview.TextFile to open a text file and read it in a word at a time (use "\\W+" as
 the second argument to the TextFile constructor). Count the words as you read them in, and
 for each word in the file, record in the ArrayList<Integer> the word count associated with
 that word—this is, in effect, the location in the file where that word was found.
*/

import net.mindview.util.TextFile;

import java.util.*;

public class Exercise1125 {

  public static void main(String[] args) {
    List<String> words = new TextFile("tmp/SetOperations.java", "\\W+");
    Map<String, ArrayList<Integer>> counts = new HashMap<>();

    int i = 0;
    for (String word : words) {
      ArrayList<Integer> count = counts.get(word);
      if (count == null) {
        count = new ArrayList<>();
        counts.put(word, count);
      }

      count.add(i);
      i ++;
    }

    System.out.println(counts);
  }
}