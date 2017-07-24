//: holding/UniqueWordsAlphabetic.java
package holding; /* Added by Eclipse.py */


/* Exercise 11.22
 Modify the previous exercise so that it uses a class containing a String
 and a count field to store each different word, and a Set of these objects to maintain the list
 of words.
*/

import net.mindview.util.TextFile;

import java.util.*;

class Word {
  private String word;
  private int count;

  Word(String word) {
    this.word = word;
    count = 1;
  }

  public String getWord() { return word; }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public boolean equals(Object o) {
    return o instanceof Word && (word.equals(((Word)o).word));
  }

  public int hashCode() {
    return word.hashCode();
  }

  public String toString() {
    return word + "(" + count + ")";
  }
}

public class Exercise1122 {

  public static void main(String[] args) {
    List<String> words = new TextFile("tmp/SetOperations.java", "\\W+");
    Set<Word> wordSet = new HashSet<>();

    for (String word : words) {
      boolean has = false;
      for (Word W : wordSet) {
        if (word.equals(W.getWord())) {
          W.setCount(W.getCount() + 1);
          has = true;
          break;
        }
      }
      if (! has)
        wordSet.add(new Word(word));
    }
    System.out.println(wordSet);
  }
}