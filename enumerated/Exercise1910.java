//: enumerated/VendingMachine.java
// {Args: VendingMachineInput.txt}
package enumerated;

/* Exercise 19.10
 (7) Modify class VendingMachine (only) using EnumMap so that one
 program can have multiple instances of VendingMachine.
 */


import net.mindview.util.Generator;

import java.util.EnumMap;

import static net.mindview.util.Print.print;

class VendingMachine2 {
  private State state = State.RESTING;
  private int amount = 0;
  private Input selection = null;

  enum State { RESTING, ADDING_MONEY, DISPENSING, GIVING_CHANGE, TERMINAL }
  class Methods {
    void next(Input input) {
      throw new RuntimeException("Only call next(Input input) for non-transient states");
    }
    void next() {
      throw new RuntimeException("Only call next() for StateDuration.TRANSIENT states");
    }
    void output() {
      print(amount);
    }
    boolean isTransient() { return false; }
  }
  private EnumMap<State, Methods> em = new EnumMap<>(State.class);

  VendingMachine2() {
    em.put(State.RESTING, new Methods() {
      @Override
      void next(Input input) {
        switch (Category.categorize(input)) {
          case MONEY:
            amount += input.amount();
            state = State.ADDING_MONEY;
            break;
          case SHUT_DOWN:
            state = State.TERMINAL;
          default:
        }
      }
    });
    em.put(State.ADDING_MONEY, new Methods() {
      @Override
      void next(Input input) {
        switch (Category.categorize(input)) {
          case MONEY:
            amount += input.amount();
            break;
          case ITEM_SELECTION:
            selection = input;
            if (amount < selection.amount())
              print("Insufficient money for " + selection);
            else state = State.DISPENSING;
            break;
          case QUIT_TRANSACTION:
            state = State.GIVING_CHANGE;
            break;
          case SHUT_DOWN:
            state = State.TERMINAL;
          default:
        }
      }
    });
    em.put(State.DISPENSING, new Methods() {
      @Override
      void next() {
        print("here is your " + selection);
        amount -= selection.amount();
        state = State.GIVING_CHANGE;
      }
      @Override
      boolean isTransient() { return true; }
    });
    em.put(State.GIVING_CHANGE, new Methods() {
      @Override
      void next() {
        if (amount > 0) {
          print("Your change: " + amount);
          amount = 0;
        }
        state = State.RESTING;
      }
      @Override
      boolean isTransient() { return true; }
    });
    em.put(State.TERMINAL, new Methods() {
      @Override
      void output() { print("Halted"); }
    });
  }

  void run(Generator<Input> gen) {
    while (state != State.TERMINAL) {
      em.get(state).next(gen.next());
      while (em.get(state).isTransient())
        em.get(state).next();
      em.get(state).output();
    }
  }
}

public class Exercise1910 {
  public static void main(String[] args) {
    Generator<Input> gen = new FileInputGenerator("tmp/VendingMachineInput.txt");
    new VendingMachine2().run(gen);
    print();
    gen = new FileInputGenerator("tmp/VendingMachineInput.txt");
    new VendingMachine2().run(gen);
  }
}