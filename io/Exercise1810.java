//: io/BufferedInputFile.java
package io; /* Added by Eclipse.py */

/* Exercise 18.10
 (2) Modify Exercise 8 to take additional command-line arguments of words
 to find in the file. Print all lines in which any of the words match.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static net.mindview.util.Print.print;

class BufferedInputFile2 {
  // Throw exceptions to console:
  public static String read(String filename, String keyword) throws IOException {
    // Reading input by lines:
    BufferedReader in = new BufferedReader(new FileReader(filename));
    StringBuilder sb = new StringBuilder();
    String s;
    while ((s = in.readLine()) != null) {
      if (Arrays.asList(s.split("\\W+")).contains(keyword))
        sb.append(s + "\n");
    }
    in.close();
    return sb.toString();
  }
}

public class Exercise1810 {
  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      print("Usage: Exercise1810 filename keyword");
      System.exit(1);
    }
    print(BufferedInputFile2.read(args[0], args[1]));
  }
}