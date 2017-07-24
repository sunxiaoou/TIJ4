//: io/FileOutputShortcut.java
package io; /* Added by Eclipse.py */

/* Exercise 18.12
 (3) Modify Exercise 8 to also open a text file so you can write text into it.
 Write the lines in the LinkedList, along with line numbers (do not attempt to use the
 "LineNumber" classes), out to the file.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Exercise1812 {
  static String file = "tmp/FileOutputShortcut.out";
  public static void main(String[] args)
  throws IOException {
    BufferedReader in = new BufferedReader(
      new StringReader(
       BufferedInputFile.read("tmp/FileOutputShortcut.java")));
    // Here's the shortcut:
    PrintWriter out = new PrintWriter(file);
    int lineCount = 1;
    String s;
    List<String> list = new ArrayList<>();
    while((s = in.readLine()) != null )
      list.add(lineCount++ + ": " + s);
    ListIterator<String> it = list.listIterator(list.size() - 1);
    while (it.hasPrevious())
      out.println(it.previous());
    out.close();
    // Show the stored file:
    System.out.println(BufferedInputFile.read(file));
  }
}