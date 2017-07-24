//: strings/JGrep.java
package strings; /* Added by Eclipse.py */
// A very simple version of the "grep" program.
// {Args: JGrep.java "\\b[Ssct]\\w+"}

/* Exercise 13.15
 (5) Modify JGrep.java to accept flags as arguments (e.g.,
 Pattern.CASE_INSENSITIVE, Pattern.MULTILINE).
 */

import net.mindview.util.TextFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise1315 {
  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      System.out.println("Usage: java JGrep file regex [flag]");
      System.exit(0);
    }

    Pattern p;
    if (args.length == 2)
      p = Pattern.compile(args[1]);
    else {
      int flag = 0;
      for (int i = 2; i < args.length; i++)
        flag |= Integer.valueOf(args[i]);
      p = Pattern.compile(args[1], flag);
    }

    // Iterate through the lines of the input file:
    int index = 0;
    Matcher m = p.matcher("");
    for(String line : new TextFile(args[0])) {
      m.reset(line);
      while(m.find())
        System.out.println(index++ + ": " +
          m.group() + ": " + m.start());
    }
  }
}