//: concurrency/SyncObject.java
package concurrency; /* Added by Eclipse.py */
// Synchronizing on another object.

/* Exercise 21.15
 (1) Create a class with three methods containing critical sections that all
 synchronize on the same object. Create multiple tasks to demonstrate that only one of these
 methods can run at a time. Now modify the methods so that each one synchronizes on a
 different object and show that all three methods can be running at once.
 */


import java.util.concurrent.*;

import static net.mindview.util.Print.print;

public class Exercise2115 {
  private final Object syncObject = new Object();
  private void func(String m) {
    for(int i = 0; i < 5; i++) {
      print(m + " " + i);
      Thread.yield();
      try {
        TimeUnit.MILLISECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  //public void g() { synchronized(syncObject) { func("g()"); } }
  //public void f() { synchronized(syncObject) { func("f()"); } }
  public void f() { synchronized(new Object()) { func("f()"); } }
  public void g() { synchronized(new Object()) { func("g()"); } }
  public void h() { synchronized(syncObject) { func("h()"); } }

  public static void main(String[] args) {
    Exercise2115 me = new Exercise2115();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new Runnable() {
      @Override
      public void run() {
        me.f();
      }
    });
    exec.execute(new Runnable() {
      @Override
      public void run() {
        me.g();
      }
    });
    me.h();
    exec.shutdown();
  }
}

