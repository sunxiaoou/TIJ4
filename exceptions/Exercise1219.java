//: exceptions/LostMessage.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.19
 How an exception can be lost.Repair the problem in LostMessage.java by guarding the call in the
 finally clause.
 */

public class Exercise1219 {
  void f() throws VeryImportantException {
    throw new VeryImportantException();
  }
  void dispose() throws HoHumException {
    throw new HoHumException();
  }
  public static void main(String[] args) {
    try {
      Exercise1219 lm = new Exercise1219();
      try {
        lm.f();
      } finally {
        try {
          lm.dispose();
        } catch (Exception e){
        }
      }
    } catch(Exception e) {
      System.out.println(e);
    }
  }
}