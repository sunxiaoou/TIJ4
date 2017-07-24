//: enumerated/PostOffice.java
package enumerated; /* Added by Eclipse.py */
// Modeling a post office.

/* Exercise 19.08
 (6) Modify PostOffice.java so it has the ability to forward mail.
 */

import net.mindview.util.Enums;

import java.util.Iterator;

import static net.mindview.util.Print.print;

class Mail2 {
  // The NO's lower the probability of random selection:
  enum GeneralDelivery {YES,NO1,NO2,NO3,NO4,NO5}
  enum Scannability {UNSCANNABLE,YES1,YES2,YES3,YES4}
  enum Readability {ILLEGIBLE,YES1,YES2,YES3,YES4}
  enum Address {INCORRECT,OK1,OK2,OK3,OK4,OK5,OK6}
  enum ForwardAddress {MISSING,OK1,OK2,OK3,OK4,OK5}
  enum ReturnAddress {MISSING,OK1,OK2,OK3,OK4,OK5}
  GeneralDelivery generalDelivery;
  Scannability scannability;
  Readability readability;
  Address address;
  ForwardAddress forwardAddress;
  ReturnAddress returnAddress;
  static long counter = 0;
  long id = counter++;
  public String toString() { return "Mail " + id; }
  public String details() {
    return toString() +
      ", General Delivery: " + generalDelivery +
      ", Address Scanability: " + scannability +
      ", Address Readability: " + readability +
      ", Address Address: " + address +
      ", Forward Address: " + forwardAddress +
      ", Return address: " + returnAddress;
  }
  // Generate test Mail:
  public static Mail2 randomMail() {
    Mail2 m = new Mail2();
    m.generalDelivery= Enums.random(GeneralDelivery.class);
    m.scannability = Enums.random(Scannability.class);
    m.readability = Enums.random(Readability.class);
    m.address = Enums.random(Address.class);
    m.forwardAddress = Enums.random(ForwardAddress.class);
    m.returnAddress = Enums.random(ReturnAddress.class);
    return m;
  }
  public static Iterable<Mail2> generator(final int count) {
    return new Iterable<Mail2>() {
      int n = count;
      public Iterator<Mail2> iterator() {
        return new Iterator<Mail2>() {
          public boolean hasNext() { return n-- > 0; }
          public Mail2 next() { return randomMail(); }
          public void remove() { // Not implemented
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }
}

class PostOffice2 {
  enum MailHandler {
    GENERAL_DELIVERY {
      boolean handle(Mail2 m) {
        switch (m.generalDelivery) {
          case YES:
            print("Using general delivery for " + m);
            return true;
          default:
            return false;
        }
      }
    },
    MACHINE_SCAN {
      boolean handle(Mail2 m) {
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
    },
    VISUAL_INSPECTION {
      boolean handle(Mail2 m) {
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
    },
    FORWARD_TO_ANOTHER {
      boolean handle(Mail2 m) {
        switch (m.forwardAddress) {
          case MISSING:
            return false;
          default:
            print("Forwarding " + m + " to another");
            return true;
        }
      }
    },
    RETURN_TO_SENDER {
      boolean handle(Mail2 m) {
        switch (m.returnAddress) {
          case MISSING:
            return false;
          default:
            print("Returning " + m + " to sender");
            return true;
        }
      }
    };

    abstract boolean handle(Mail2 m);
  }

  static void handle(Mail2 m) {
    for(MailHandler handler : MailHandler.values())
      if(handler.handle(m))
        return;
    print(m + " is a dead letter");
  }
}

public class Exercise1908 {
  public static void main(String[] args) {
    for(Mail2 mail : Mail2.generator(10)) {
      print(mail.details());
      PostOffice2.handle(mail);
      print("*****");
    }
  }
}