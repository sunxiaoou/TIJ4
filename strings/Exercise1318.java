package strings; /* Added by Eclipse.py */

/* Exercise 13.18
 (8) Write a program that reads a Java source-code file (you provide the file
 name on the command line) and displays all the string literals in the code.
 */

import net.mindview.util.TextFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise1318 {
  public static void main(String[] args) throws Exception {
    if(args.length < 1) {
      System.out.println("Usage: java Exercise1318 file");
      System.exit(0);
    }

    String regex = "\".*\"";
    Pattern p = Pattern.compile(regex);

    // Iterate through the lines of the input file:
    int index = 0;
    Matcher m = p.matcher(TextFile.read(args[0]));
    while(m.find())
      System.out.println(index++ + ": " + m.group());
  }
}