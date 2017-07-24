//: io/DirList3.java
package io; /* Added by Eclipse.py */
// Building the anonymous inner class "in-place."
// {Args: "D.*\.java"}

/* Exercise 18.01
 (3) Modify DirList.java (or one of its variants) so that the FilenameFilter
 opens and reads each file (using the net.mindview.util.TextFile utility) and accepts the
 file based on whether any of the trailing arguments on the command line exist in that file.
*/

import net.mindview.util.TextFile;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.regex.Pattern;

import static net.mindview.util.Print.print;

class DirList4 {
  DirList4(final String[] args) {
    File path = new File(".");
    String[] list;
    list = path.list(new FilenameFilter() {
      private Pattern pattern = Pattern.compile(args[0]);
      public boolean accept(File dir, String name) {
        if (pattern.matcher(name).matches()) {
          List<String> words = new TextFile(name, "\\W+");
          if(words.contains(args[1]))
            return true;
        }
        return false;
      }
    });
    Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
    for (String dirItem : list)
      System.out.println(dirItem);
  }
}

public class Exercise1801 {
  public static void main(String[] args) {
    if (args.length < 2) {
      print("Usage: Exercise1801 regex keyword");
      System.exit(1);
    }
    new DirList4(args);
  }
}