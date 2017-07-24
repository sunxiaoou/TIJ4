//: holding/UniqueWordsAlphabetic.java
package io; /* Added by Eclipse.py */


/* Exercise 18.32
 (4) Using a Map<String,Integer> and the
 net.mindview.util.TextFile utility, write a program that counts the occurrence of words
 in a file (use "\\W+" as the second argument to the TextFile constructor). Store the results
 as an XML file.
*/


import net.mindview.util.TextFile;
import nu.xom.Document;
import nu.xom.Element;
import xml.Person;

import java.util.*;

// import static net.mindview.util.Print.print;

public class Exercise1832 {
  public static void main(String[] args) throws Exception {
    List<String> words = new TextFile("tmp/SetOperations.java", "\\W+");
    Map<String, Integer> map = new HashMap<>();
    for (String word : words) {
      Integer count = map.get(word);
      count = (count == null) ? 1 : count + 1;
      map.put(word, count);
    }
    // print(map);
    Set<String> keys = map.keySet();
    Element root = new Element("count");
    for (String key : keys) {
      Element word = new Element("word");
      Element string = new Element("string");
      string.appendChild(key);
      Element quantity = new Element("quantity");
      quantity.appendChild(map.get(key).toString());
      word.appendChild(string);
      word.appendChild(quantity);
      root.appendChild(word);
    }
    Document doc = new Document(root);
    Person.format(System.out, doc);
  }
}