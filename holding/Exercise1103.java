package holding; /* Added by Eclipse.py */
// Holds a sequence of Objects.

/* Exercise 11.03
 Modify innerclasses/Sequence.java so that you can add any number
 of elements to it.
*/


import java.util.*;

interface Selector {
    boolean end();
    Object current();
    void next();
}

class Sequence3 {
    private List items;

    public Sequence3() {
        items = new ArrayList<>();
    }

    public void add(String x) {
        items.add(x);
    }

    public Selector selector() {
        return new Selector() {
            private int i = 0;
            public boolean end() {
                return i == items.size();
            }
            public Object current() {
                return items.get(i);
            }
            public void next() {
                if (i < items.size()) i++;
            }
        };
    }
}

public class Exercise1103 {
    public static void main(String[] args) {
        Sequence3 sequence = new Sequence3();

        for(int i = 0; i < 10; i++) {
            sequence.add(Integer.toString(i));
        }

        Selector selector = sequence.selector();

        while(!selector.end()) {
            System.out.print(selector.current() + " ");
            selector.next();
        }

        System.out.println();
    }
} /* Output:
0 1 2 3 4 5 6 7 8 9
*///:~
