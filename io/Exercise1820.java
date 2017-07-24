//: net/mindview/util/TextFile.java
// Static functions for reading and writing text files as
// a single string, and treating a file as an ArrayList.
package io;

/* Exercise 18.20
 Exercise 20: (4) Using Directory.walk( ) and BinaryFile, verify that all .class files
 in a directory tree begin with the hex characters ‘CAFEBABE’.
 */

import net.mindview.util.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import static net.mindview.util.Print.print;

public class Exercise1820 extends ArrayList<String> {
  public static void main(String[] args) {
    File root = new File("bin");
    for (File file : Directory.walk(root.getAbsolutePath(), ".*\\.class")) {
      try {
        byte[] bytes = BinaryFile.read(file);
        if (bytes[0] != (byte)0xca ||
                bytes[1] != (byte)0xfe ||
                bytes[2] != (byte)0xba ||
                bytes[3] != (byte)0xbe)
          print(file + " is not a cafe baby!");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}