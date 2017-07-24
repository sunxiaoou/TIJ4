//: exceptions/InheritingExceptions.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.07
 Modify Exercise 3 so that the catch clause logs the results
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

public class Exercise1207 {
  private static Logger logger = Logger.getLogger("Exercise1207");
  static void logException(Exception e) {
    StringWriter trace = new StringWriter();
    e.printStackTrace(new PrintWriter(trace));
    logger.severe(trace.toString());
  }

  public static void main(String[] args) {
    Integer a[] = new Integer[5];
    try {
      for (int i = 0; i < 6; i ++)
        a[i] = i;
    } catch(ArrayIndexOutOfBoundsException e) {
      logException(e);
    }
  }
}