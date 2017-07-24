//: net/mindview/util/ProcessFiles.java
package io;

import net.mindview.util.Directory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.mindview.util.Print.print;

/* Exercise 18.06
 (5) Use ProcessFiles to find all the Java source-code files in a particular
 directory subtree that have been modified after a particular date.
 */

class ProcessFiles {
  public interface Strategy {
    void process(File file);
  }

  private Strategy strategy;
  private String ext;

  public ProcessFiles(Strategy strategy, String ext) {
    this.strategy = strategy;
    this.ext = ext;
  }

  public void start(String[] args) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
      Date date = sdf.parse(args[1]);
      long timeInMillis = date.getTime();
      File root = new File(args[0]);
      for (File file : Directory.walk(root.getAbsolutePath(), ".*\\." + ext))
        if (file.lastModified() > timeInMillis)
          strategy.process(file.getCanonicalFile());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}

public class Exercise1806 {
  public static void main(String[] args) {
    if (args.length < 2) {
      print("Usage: Exercise1806 dir date(yyyyMMdd)");
      System.exit(1);
    }
    new ProcessFiles(new ProcessFiles.Strategy() {
      public void process(File file) {
        print(file);
      }
    }, "java").start(args);
  }
}