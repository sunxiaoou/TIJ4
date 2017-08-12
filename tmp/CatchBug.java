//: enumerated/RoShamBo6.java
// Enums using "tables" instead of multiple dispatch.
package tmp;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class CatchBug {
  private static Random rand = new Random(47);
  private static Monkey monkeys[];
  private static Monkey work[];
  //  private static int dead = 0;

  enum Roll {
    GOOD, MEDIUM, BAD;
    // Good monkey always catches bugs for others
    // Medium monkey is tit for tat
    // Bad monkey never catches bugs for others

    private static final int[][][] table = {
            // GOOD    MEDIUM  BAD
            { {2, 2}, {2, 2}, {0, 2} }, // GOOD
            { {2, 2}, {2, 2}, {0, 1} }, // MEDIUM
            { {2, 0}, {0, 0}, {0, 0} } // BAD
    };

    private static int [] quantities = new int[3];

    int[] compete(Roll other) { return table[ordinal()][other.ordinal()]; }

    static void clean() { Arrays.fill(quantities, 0); }
    static int quantity(Roll roll) { return quantities[roll.ordinal()]; }
    static boolean isExtinct() {
      for (int i = 0; i < quantities.length; i ++)
        if (quantities[i] == 0)
          return true;
      return false;
    }
    void born() { quantities[ordinal()] ++; }

    Roll reborn() {
      Roll other = null;
      quantities[ordinal()] --;
      switch (this) {
        case GOOD:
          other = rand.nextInt(2) == 0 ? MEDIUM : BAD;
          break;
        case MEDIUM:
          other = rand.nextInt(2) == 0 ? BAD : GOOD;
          break;
        case BAD:
          other = rand.nextInt(2) == 0 ? GOOD : MEDIUM;
          break;
      }
      quantities[other.ordinal()] ++;
      return other;
    }
  }

  static class Monkey {
    private static int counter = 0;
    private final int id = counter ++;
    private Roll roll;
    private static final int increment = 2;
    private static final int max_bugs = 10;
    private static int deadQuantity = 0;
    private int value = 0;    // current number of bugs

    Monkey(Roll roll) {
      roll.born();
      this.roll = roll;
    }

    Roll getRoll() { return roll; }
    // void setRoll(Roll roll) { this.roll = roll; }
    // int getValue() { return value; }
    // void setValue(int value) { this.value = value; }
    void increase() { value += increment; }
    void decrease(int a) { value = value > a ? value - a : 0; }

    void compete(Monkey other) {
      increase();
      other.increase();
      int result[] = roll.compete(other.getRoll());
      decrease(result[0]);
      other.decrease(result[1]);
    }

    private void reborn() {
      if (value > max_bugs) {     // died and reborn
        value = 0;
        roll = roll.reborn();
        deadQuantity ++;
      }
    }

    static void clean() { deadQuantity = 0; }
    static int deadQuantity() { return deadQuantity; }

    public String toString() { return String.format("%03d_%s(%d)", id, roll, value); }
  }

  private static void print(String good) {
    for (Monkey monkey : monkeys)
      System.out.print(monkey + ", ");
    System.out.println();
  }

  private static String track(String roll, int quantity) {
    StringBuilder s = new StringBuilder(roll);
    int number = roll.equals("AbnormalDead") ? quantity / 100 : quantity / 10;
    for(int i = 0; i < number; i ++)
      s.append("*");
    s.append("----").append(quantity);
    if (! roll.equals("AbnormalDead") && quantity == 0)
      s.append(" (is extinct)");

    return s.toString();
  }

  private static void count() {
    System.out.println(track("Good", Roll.quantity(Roll.GOOD)));
    System.out.println(track("Medium", Roll.quantity(Roll.MEDIUM)));
    System.out.println(track("Bad", Roll.quantity(Roll.BAD)));
    System.out.println(track("AbnormalDead", Monkey.deadQuantity()));
  }

  private static void shuttle() {
    for (int i = 0; i < work.length; i ++) {
      Monkey tmp = work[i];
      int idx = rand.nextInt(work.length);
      work[i] = work[idx];
      work[idx] = tmp;
    }
  }

  private CatchBug(int nGood, int nMedium, int nBad, int nRound) throws IOException {
    Roll.clean();
    Monkey.clean();
    if ((nGood + nMedium + nBad) % 2 != 0)
      nGood ++;
    monkeys = new Monkey[nGood + nMedium + nBad];
    work = new Monkey[nGood + nMedium + nBad];
    int i = 0;
    for (; i < nGood; i ++)
      monkeys[i] = new Monkey(Roll.GOOD);
    for (; i < nGood + nMedium; i ++)
      monkeys[i] = new Monkey(Roll.MEDIUM);
    for (; i < nGood + nMedium + nBad; i ++)
      monkeys[i] = new Monkey(Roll.BAD);

    // System.out.printf("\n\nGood(%d)Medium(%d)Bad(%d)\n", nGood, nMedium, nBad);
    System.out.printf("\n\nTest begins ...\n");
    count();
    for (i = 0; i < nRound; i ++) {
      System.arraycopy(monkeys, 0, work, 0, monkeys.length);
      shuttle();
      // print();

      for (int j = 0; j < monkeys.length; j += 2) {
        Monkey a = work[j];
        Monkey b = work[j + 1];
        a.compete(b);
        // System.out.printf("%s vs. %s\n", a, b);
      }

      for (Monkey monkey : work)
        monkey.reborn();

      /*
      if (i % 10 == 0) {
        System.out.printf("%03d: \n", i);
        count();
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      */

      if (Roll.isExtinct())
        break;
    }
    System.out.printf("\nTest ends at Round %03d:\n", i);
    count();
    // System.out.println("Input any key to continue ...");
    // System.in.read();
  }

  public static void main(String[] args) throws Exception {
    new CatchBug(400, 400, 400, 200);
    new CatchBug(1000, 100, 100, 200);
    new CatchBug(100, 1000, 100, 200);
    new CatchBug(100, 100, 1000, 200);
  }
}