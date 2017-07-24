//: innerclasses/Sequence.java
package holding; /* Added by Eclipse.py */
// Holds a sequence of Objects.

import java.util.*;

/* Exercise
 Modify innerclasses/Sequence.java so that Sequence works with an
 Iterator instead of a Selector.
*/

class Sequence4 {
    List<String> items;
    public Sequence4() {
        items = new ArrayList<>();
    }
    public void add(String x) {
        items.add(x);
    }
}

public class Exercise1109 {
    public static void main(String[] args) {
        Sequence4 sequence = new Sequence4();

        for(int i = 0; i < 10; i++) {
            sequence.add(Integer.toString(i));
        }

        Iterator selector = sequence.items.iterator();

        while (selector.hasNext()) {
            System.out.print(selector.next() + " ");
        }

        System.out.println();
    }
} /* Output:
0 1 2 3 4 5 6 7 8 9
*///:~
