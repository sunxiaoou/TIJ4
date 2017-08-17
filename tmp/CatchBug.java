//: enumerated/RoShamBo6.java
// Enums using "tables" instead of multiple dispatch.
package tmp;

import java.util.Arrays;
import java.util.Random;


public class CatchBug {
  private static int counter = 0;
  private final int id =  ++ counter;
  private static Random rand = new Random(47);
  private static Monkey monkeys[];
  private static Monkey work[];

  private static int fieldWidth = 10;
  private static String stringField() { return "%" + fieldWidth + "s"; }
  private static String numberField() {
    return "%" + fieldWidth + "d";
  }
  private static int sizeWidth = 7;
  private static String sizeField = "%" + sizeWidth + "s";
  private String headline = "";


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

  private void displayHeader() {
    // Calculate width and pad with '-':
    int width = fieldWidth * (Roll.values().length + 2) + sizeWidth;
    int dashLength = width - headline.length() - 1;
    StringBuilder head = new StringBuilder(width);
    for(int i = 0; i < dashLength / 2; i++)
      head.append('-');
    head.append(' ');
    head.append(headline);
    head.append(' ');
    for(int i = 0; i < dashLength/2; i++)
      head.append('-');
    System.out.println(head);
    // Print column headers:
    System.out.format(sizeField, "Round#");
    for(Roll roll : Roll.values())
      System.out.format(stringField(), roll);
    System.out.format(stringField(), "Killed");
    System.out.format(stringField(), "K/R");

    System.out.println();
  }

  private static void print(int round) {
    System.out.format(sizeField, round);
    for(Roll roll : Roll.values())
      System.out.format(numberField(), Roll.quantity(roll));
      System.out.format(numberField(), Monkey.deadQuantity());
      System.out.format(numberField(), round == 0 ? 0 : Monkey.deadQuantity() / round);

    System.out.println();
  }

  private static void shuttle() {
    for (int i = 0; i < work.length; i ++) {
      Monkey tmp = work[i];
      int idx = rand.nextInt(work.length);
      work[i] = work[idx];
      work[idx] = tmp;
    }
  }

  private CatchBug(int nGood, int nMedium, int nBad, int nRound) {
    headline = String.format("Test%02d", id);
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

    displayHeader();
    print(0);
    // for (i = 0; i < nRound; i ++) {
    for (i = 0; ; i ++) {
      System.arraycopy(monkeys, 0, work, 0, monkeys.length);
      shuttle();

      for (int j = 0; j < monkeys.length; j += 2) {
        Monkey a = work[j];
        Monkey b = work[j + 1];
        a.compete(b);
        // System.out.printf("%s vs. %s\n", a, b);
      }

      for (Monkey monkey : work)
        monkey.reborn();

      if (Roll.isExtinct())
        break;
    }
    print(i + 1);
  }

  public static void main(String[] args) {
    new CatchBug(400, 400, 400, 200);
    new CatchBug(1000, 100, 100, 200);
    new CatchBug(100, 1000, 100, 200);
    new CatchBug(100, 100, 1000, 200);
  }
}