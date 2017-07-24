//: holding/UniqueWords.java
package containers; /* Added by Eclipse.py */

/* Exercise 17.04
(2) Create a Collection initializer that opens a file and breaks it into words
using TextFile, and then uses the words as the source of data for the resulting Collection.
Demonstrate that it works.
 */


import net.mindview.util.TextFile;

import java.util.Collection;
import java.util.TreeSet;

public class Exercise1704 {
  public static void main(String[] args) {
    Collection<String> words = new TreeSet<String>(
      new TextFile("tmp/SetOperations.java", "\\W+"));
    System.out.println(words);
  }
}