//: enumerated/PostOffice.java
package enumerated; /* Added by Eclipse.py */
// Modeling a post office.

/* Exercise 19.09
 (5) Modify class PostOffice so that it uses an EnumMap.
 */

import java.util.EnumMap;
import java.util.Map;

import static net.mindview.util.Print.print;

class PostOffice3 {
  enum MailHandler { GENERAL_DELIVERY, MACHINE_SCAN, VISUAL_INSPECTION, RETURN_TO_SENDER }
  interface Command { boolean handle(Mail m); }
  private static EnumMap<MailHandler, Command> em = new EnumMap<>(MailHandler.class);
  static {
    em.put(MailHandler.GENERAL_DELIVERY, new Command() {
      @Override
      public boolean handle(Mail m) {
        switch (m.generalDelivery) {
          case YES:
            print("Using general delivery for " + m);
            return true;
          default:
            return false;
        }
      }
    });
    em.put(MailHandler.MACHINE_SCAN, new Command() {
      @Override
      public boolean handle(Mail m) {
        switch (m.scannability) {
          case UNSCANNABLE:
            return false;
          default:
            switch (m.address) {
              case INCORRECT:
                return false;
              default:
                print("Delivering " + m + " automatically");
                return true;
            }
        }
      }
    });
    em.put(MailHandler.VISUAL_INSPECTION, new Command() {
      @Override
      public boolean handle(Mail m) {
        switch (m.readability) {
          case ILLEGIBLE:
            return false;
          default:
            switch (m.address) {
              case INCORRECT:
                return false;
              default:
                print("Delivering " + m + " normally");
                return true;
            }
        }
      }
    });
    em.put(MailHandler.RETURN_TO_SENDER, new Command() {
      @Override
      public boolean handle(Mail m) {
        switch (m.returnAddress) {
          case MISSING:
            return false;
          default:
            print("Returning " + m + " to sender");
            return true;
        }
      }
    });
  }

  static void handle(Mail m) {
    for (Map.Entry<MailHandler, Command> entry : em.entrySet())
      if(entry.getValue().handle(m))
        return;
    print(m + " is a dead letter");
  }
}

public class Exercise1909 {
  public static void main(String[] args) {
    for(Mail mail : Mail.generator(10)) {
      print(mail.details());
      PostOffice3.handle(mail);
      print("*****");
    }
  }
}