//: net/mindview/util/OSExecute.java
// Run an operating system command
// and send the output to the console.
package io;

/* Exercise 18.22
 (5) Modify OSExecute.java so that, instead of printing the standard
 output stream, it returns the results of executing the program as a List of Strings.
 Demonstrate the use of this new version of the utility.
 */

import net.mindview.util.OSExecuteException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static net.mindview.util.Print.print;

class OSExecute {
  public static List<String> command(String command) {
    List<String> list = new ArrayList<>();
    boolean err = false;
    try {
      Process process = new ProcessBuilder(command.split(" ")).start();
      BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String s;
      while((s = results.readLine())!= null)
        list.add(s);
      BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
      // Report errors and return nonzero value
      // to calling process if there are problems:
      while((s = errors.readLine())!= null) {
        if(s.startsWith("Error:"))
          err = true;
        list.add(s);
      }
    } catch(Exception e) {
      // Compensate for Windows 2000, which throws an
      // exception for the default command line:
      if(!command.startsWith("CMD /C"))
        command("CMD /C " + command);
      else
        throw new RuntimeException(e);
    }
    if(err) {
      throw new OSExecuteException("Errors executing " + command);
    }
    return list;
  }
}

public class Exercise1822 {
  public static void main(String[] args) {
    for (String s : OSExecute.command("javap bin/io/OSExecuteDemo"))
      print(s);
  }
}