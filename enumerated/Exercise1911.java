//: enumerated/VendingMachine.java
// {Args: VendingMachineInput.txt}
package enumerated;

/* Exercise 19.11
 (7) In a real vending machine you will want to easily add and change the
 type of vended items, so the limits imposed by an enum on Input are impractical
 (remember that enums are for a restricted set of types). Modify VendingMachine.java so
 that the vended items are represented by a class instead of being part of Input, and
 initialize an Array List of these objects from a text file (using
 net.mindview.util.TextFile).
*/


import net.mindview.util.Generator;
import net.mindview.util.TextFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.mindview.util.Print.*;

interface VendingInput {
  int amount();
}

class Money implements VendingInput {
  private String name;
  private int value;
  Money(String name, int value) { this.name = name; this.value = value; }
  public int amount() { return value; }
  public String toString() { return name; }
}

class ItemSelection implements VendingInput {
  private String name;
  private int value;
  ItemSelection(String name, int value) { this.name = name; this.value = value; }
  public int amount() { return value; }
  public String toString() { return name; }
}

class QuitTransaction implements VendingInput {
  public int amount() { // Disallow
    throw new RuntimeException("ABORT.amount()");
  }
}

class ShutDown implements VendingInput {
  public int amount() { // Disallow
    throw new RuntimeException("SHUT_DOWN.amount()");
  }
}

class VendingMachine3 {
  private static State state = State.RESTING;
  private static int amount = 0;
  private static VendingInput selection = null;

  enum StateDuration {TRANSIENT} // Tagging enum
  enum State {
    RESTING {
      void next(VendingInput input) {
        switch (input.getClass().getSimpleName()) {
          case "Money":
            amount += input.amount();
            state = ADDING_MONEY;
            break;
          case "ShutDown":
            state = TERMINAL;
          default:
        }
      }
    },
    ADDING_MONEY {
      void next(VendingInput input) {
        switch (input.getClass().getSimpleName()) {
          case "Money":
            amount += input.amount();
            break;
          case "ItemSelection":
            selection = input;
            if (amount < selection.amount())
              print("Insufficient money for " + selection);
            else state = DISPENSING;
            break;
          case "QuitTransaction":
            state = GIVING_CHANGE;
            break;
          case "ShutDown":
            state = TERMINAL;
          default:
        }
      }
    },
    DISPENSING(StateDuration.TRANSIENT) {
      void next() {
        print("here is your " + selection);
        amount -= selection.amount();
        state = GIVING_CHANGE;
      }
    },
    GIVING_CHANGE(StateDuration.TRANSIENT) {
      void next() {
        if (amount > 0) {
          print("Your change: " + amount);
          amount = 0;
        }
        state = RESTING;
      }
    },
    TERMINAL {
      void output() {
        print("Halted");
      }
    };
    private boolean isTransient = false;

    State() {}
    State(StateDuration trans) {
      isTransient = true;
    }

    void next(VendingInput input) {
      throw new RuntimeException("Only call next(Input input) for non-transient states");
    }
    void next() {
      throw new RuntimeException("Only call next() for StateDuration.TRANSIENT states");
    }
    void output() {
      print(amount);
    }
  }

  private static QuitTransaction quitTransaction = new QuitTransaction();
  private static ShutDown shutDown = new ShutDown();
  private static List<Money> moneys = new ArrayList<>();
  private static List<ItemSelection> items = new ArrayList<>();

  static void initialize(String fileName) {
    moneys.add(new Money("NICKEL", 5));
    moneys.add(new Money("DIME", 10));
    moneys.add(new Money("QUARTER", 25));
    moneys.add(new Money("DOLLAR", 100));

    // parse format as "TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),"
    for (String s : new TextFile(fileName, ",")) {
      Matcher m = Pattern.compile("(\\w+)\\((\\w+)\\)").matcher(s);
      if (m.find()) {
        String name = m.group(1);
        int value = Integer.parseInt(m.group(2));
        items.add(new ItemSelection(name, value));
      }
    }
    // print(items);
  }

  static VendingInput getInput(String name) {
    switch (name) {
      case "ABORT_TRANSACTION":
        return quitTransaction;
      case "STOP":
        return shutDown;
      default:
        for (Money money : moneys) {
          if (money.toString().equals(name))
            return money;
        }
        for (ItemSelection item : items) {
          if (item.toString().equals(name))
            return item;
        }
      return null;
    }
  }
  static void run(Generator<VendingInput> gen) {
    while (state != State.TERMINAL) {
      state.next(gen.next());
      while (state.isTransient)
        state.next();
      state.output();
    }
  }
}

class FileInputGenerator3 implements Generator<VendingInput> {
  private Iterator<String> input;
  FileInputGenerator3(String fileName) {
    input = new TextFile(fileName, ";").iterator();
  }
  public VendingInput next() {
    return VendingMachine3.getInput(input.next().trim());
  }
}

public class Exercise1911 {
  public static void main(String[] args) {
    VendingMachine3.initialize("tmp/VendingMachineItem.txt");
    Generator<VendingInput> gen = new FileInputGenerator3("tmp/VendingMachineInput.txt");
    VendingMachine3.run(gen);
  }
}