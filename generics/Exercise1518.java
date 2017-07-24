//: generics/BankBigFish.java
package generics; /* Added by Eclipse.py */
// A very simple bank BigFish simulation.
import java.util.*;
import net.mindview.util.*;

/* Exercise 15.18
 (3) Following the form of BankTeller.java, create an example where
 BigFish eat LittleFish in the Ocean.
 */


class LittleFish {
    private static long counter = 1;
    private final long id = counter++;
    private LittleFish() {}
    public String toString() { return "LittleFish " + id; }
    // A method to produce Generator objects:
    public static Generator<LittleFish> generator() {
        return new Generator<LittleFish>() {
            public LittleFish next() { return new LittleFish(); }
        };
    }
}

class BigFish {
    private static long counter = 1;
    private final long id = counter++;
    private BigFish() {}
    public String toString() { return "BigFish " + id; }
    // A single Generator object:
    public static Generator<BigFish> generator =
            new Generator<BigFish>() {
                public BigFish next() { return new BigFish(); }
            };
}

public class Exercise1518 {
    public static void eat(BigFish t, LittleFish c) {
        System.out.println(t + " eats " + c);
    }
    public static void main(String[] args) {
        Random rand = new Random(47);
        Queue<LittleFish> line = new LinkedList<>();
        Generators.fill(line, LittleFish.generator(), 15);
        List<BigFish> BigFishs = new ArrayList<>();
        Generators.fill(BigFishs, BigFish.generator, 4);
        for(LittleFish c : line)
            eat(BigFishs.get(rand.nextInt(BigFishs.size())), c);
    }
}