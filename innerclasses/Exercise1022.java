//: innerclasses/Sequence.java
package innerclasses; /* Added by Eclipse.py */
// Holds a sequence of Objects.

/*
Exercise 22: (2) Implement reverseSelector( ) in Sequence.java.
 */

class Sequence2 {
    private Object[] items;
    private int next = 0;

    public Sequence2(int size) {
        items = new Object[size];
    }

    public void add(Object x) {
        if (next < items.length)
            items[next++] = x;
    }

    public Selector selector() {
        return new Selector() {
            private int i = 0;

            public boolean end() {
                return i == items.length;
            }

            public Object current() {
                return items[i];
            }

            public void next() {
                if (i < items.length) i++;
            }
        };
    }

    // exercise 10.22: add a reverse selector method
    public Selector reverseSelector() {
        return new Selector() {
            private int i = items.length;

            public boolean end() {
                return i == 0;
            }

            public Object current() {
                return items[i - 1];
            }

            public void next() {
                if (i > 0) i--;
            }
        };
    }
}

public class Exercise1022 {
    public static void main(String[] args) {
        Sequence2 sequence = new Sequence2(10);

        for(int i = 0; i < 10; i++) {
            sequence.add(Integer.toString(i));
        }

        Selector selector = sequence.selector();

        while(!selector.end()) {
            System.out.print(selector.current() + " ");
            selector.next();
        }

        System.out.println();

        selector = sequence.reverseSelector();
        while(!selector.end()) {
            System.out.print(selector.current() + " ");
            selector.next();
        }
    }
}