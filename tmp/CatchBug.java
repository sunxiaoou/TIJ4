//: enumerated/RoShamBo6.java
// Enums using "tables" instead of multiple dispatch.
package tmp;

import java.util.Random;

public class CatchBug {
  private static Random rand = new Random(47);
  private static Monkey monkeys[];
  private static Monkey work[];

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
    public int[] compete(Roll other) {
      return table[this.ordinal()][other.ordinal()];
    }

    public Roll reborn() {
      switch (this) {
        case GOOD:
          return rand.nextInt(2) == 0 ? MEDIUM : BAD;
        case MEDIUM:
          return rand.nextInt(2) == 0 ? BAD : GOOD;
        case BAD:
          return rand.nextInt(2) == 0 ? GOOD : MEDIUM;
      }
      return null;
    }
  }

  static class Monkey {
    private static int counter = 0;
    private final int id = counter ++;
    private Roll roll;
    private static final int increment = 2;
    private static final int max_bugs = 10;
    private int value = 0;    // current number of bugs

    Monkey(Roll roll) { this.roll = roll; }
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
      }
    }

    public String toString() { return String.format("%03d_%s(%d)", id, roll, value); }
  }

  private static void print() {
    for (Monkey monkey : monkeys)
      System.out.print(monkey + ", ");
    System.out.println();
  }

  private static void count() {
    int nGood = 0, nMedium = 0, nBad = 0;
    for (Monkey monkey : monkeys) {
      switch (monkey.getRoll()) {
        case GOOD:
          nGood ++;
          break;
        case MEDIUM:
          nMedium ++;
          break;
        case BAD:
          nBad ++;
          break;
      }
    }
    System.out.printf("GOOD(%d)MEDIUM(%d)BAD(%d)\n", nGood, nMedium, nBad);
  }

  private static void shuttle() {
    for (int i = 0; i < work.length; i ++) {
      Monkey tmp = work[i];
      int idx = rand.nextInt(work.length);
      work[i] = work[idx];
      work[idx] = tmp;
    }
  }

  private static void oneRound() {
    System.arraycopy(monkeys, 0, work, 0, monkeys.length);
    shuttle();
    // print();

    for (int i = 0; i < monkeys.length; i += 2) {
      Monkey a = work[i];
      Monkey b = work[i + 1];
      a.compete(b);
      // System.out.printf("%s vs. %s\n", a, b);
    }

    for (Monkey monkey : work)
      monkey.reborn();
  }

  private CatchBug(int nGood, int nMedium, int nBad, int nRound) {
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

    for (i = 0; i < nRound; i ++) {
      oneRound();
      if (i % 100 == 0) {
        System.out.printf("%03d: ", i);
        count();
      }
    }
  }

  public static void main(String[] args) {
    new CatchBug(1000, 100, 100, 100000);
  }
}