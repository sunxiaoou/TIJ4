//: operators/MathOps.java
package tmp; /* Added by Eclipse.py */
// Demonstrates the mathematical operators.

import java.util.*;

import static net.mindview.util.Print.*;

public class Assignment {
  public static void main(String[] args) {
    if (args.length < 5) {
      print("Usage: Assignment bigCapacity bigPrice smallCapacity smallPrice totalNum");
      System.exit(1);
    }

    printf("bigCapacity(%s) bigPrice(%s) smallCapacity(%s) smallPrice(%s) totalNum(%s)\n",
            args[0], args[1], args[2], args[3], args[4]);
    print();

    int bigCapacity = Integer.parseInt(args[0]);
    int bigPrice = Integer.parseInt(args[1]);
    int smallCapacity = Integer.parseInt(args[2]);
    int smallPrice = Integer.parseInt(args[3]);
    int totalNum = Integer.parseInt(args[4]);

    int bigMax = totalNum / bigCapacity;
    if (totalNum % bigCapacity != 0)
      bigMax ++;

    int j = 0;
    int quantity;
    int amount;
    List<Integer> amounts = new ArrayList<>();
    for (int i = bigMax; i >= 0; i --) {
      int d = totalNum - bigCapacity * i;
      if (d > 0) {
        j = d / smallCapacity;
        if (d % smallCapacity != 0)
          j++;
      }
      quantity = i * bigCapacity + j * smallCapacity;
      amount = i * bigPrice + j * smallPrice;
      // print("big(" + i + ") small(" + j + ") quantity(" + quantity + ") amount(" + amount + ")");
      printf("big(%d) small(%d) quantity(%d) amount(%d)\n", i, j, quantity, amount);
      amounts.add(amount);
    }
    print();
    amount = Collections.min(amounts);
    print("The cheapest solution is: " + (amounts.indexOf(amount) + 1));
  }
}