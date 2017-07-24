//: io/DirList3.java
package io; /* Added by Eclipse.py */
// Building the anonymous inner class "in-place."
// {Args: "D.*\.java"}

/* Exercise 18.03
 Exercise 3: (3) Modify DirList.java (or one of its variants) so that it sums up the file
 sizes of the selected files.
*/

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

import static net.mindview.util.Print.print;

class DirList5 {
  DirList5(final String[] args) {
    File path = new File(".");
    String[] list;
    list = path.list(new FilenameFilter() {
      private Pattern pattern = Pattern.compile(args[0]);
      public boolean accept(File dir, String name) {
        return pattern.matcher(name).matches();
      }
    });
    Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
    int size = 0;
    for (String dirItem : list) {
      File file = new File(dirItem);
      print(dirItem + " " + file.length());
      size += file.length();
    }
    print(size);
  }
}

public class Exercise1803 {
  public static void main(String[] args) {
    if (args.length < 1) {
      print("Usage: Exercise1801 regex");
      System.exit(1);
    }
    new DirList5(args);
  }
}