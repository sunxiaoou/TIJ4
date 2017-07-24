//: concurrency/CachedThreadPool.java
package concurrency; /* Added by Eclipse.py */

/*  Exercise 21.01
 (2) Implement a Runnable. Inside run( ), print a message, and then call
 yield( ). Repeat this three times, and then return from run( ). Put a startup message in the
 constructor and a shutdown message when the task terminates. Create a number of these
 tasks and drive them using threads.
*/

/* Exercise 21.03
 (1) Repeat Exercise 1 using the different types of executors shown in this
 section.
*/


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.mindview.util.Print.*;

class MyRun implements Runnable {
  private static int taskCount = 0;
  private final int id = taskCount ++;
  MyRun(String startup) { printf("#%d: %s\n", id, startup); }
  public void run() {
    for (int i = 0; i < 3; i ++) {
      printf("#%d: Message%d\n", id, i + 1);
      Thread.yield();
    }
    printf("#%d: Shutdown\n", id);
  }
}

public class Exercise2103 {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < 5; i++)
      exec.execute(new MyRun("Startup"));
    exec.shutdown();
  }
}