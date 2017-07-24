//: strings/JGrep.java
package io; /* Added by Eclipse.py */
// A very simple version of the "grep" program.
// {Args: JGrep.java "\\b[Ssct]\\w+"}

/* Exercise 18.26
 (3) Modify strings/JGrep.java to use Java nio memory mapped files.
*/

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.mindview.util.Print.*;

public class Exercise1826 {
  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      System.out.println("Usage: java JGrep file regex");
      System.exit(0);
    }
    FileChannel fc = new FileInputStream(new File(args[0])).getChannel();
    ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    String encoding = System.getProperty("file.encoding");
    String str = Charset.forName(encoding).decode(bb).toString();
    // print(str);

    Pattern p = Pattern.compile(args[1]);
    // Iterate through the lines of the input file:
    int index = 0;
    Matcher m = p.matcher("");
    for(String line : str.split("\n")) {
      m.reset(line);
      while(m.find())
        print(index++ + ": " + m.group() + ": " + m.start());
    }
  }
}