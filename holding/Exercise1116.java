package holding; /* Added by Eclipse.py */

/* Exercise 11.16:
 Create a Set of the vowels. Working from UniqueWords.Java, count and display the
 number of vowels in each input word, and also display the total number of vowels
 in the input file.
*/

import net.mindview.util.TextFile;
import java.util.*;

public class Exercise1116 {
  public static void main(String[] args) {
    Set<Character> vowels = new TreeSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
    List<String> words = new ArrayList<>(new TextFile("tmp/UniqueWords.java", "\\W+"));

    int total = 0;
    for (String word : words) {
      char array[] = word.toLowerCase().toCharArray();
      int num = 0;
      for (char c : array) {
        if (vowels.contains(c))
          num ++;
      }
      System.out.println("\"" + word + "(" + num + ")\"");
      total += num;
    }
    System.out.println("total vowels is " + total);
  }

}