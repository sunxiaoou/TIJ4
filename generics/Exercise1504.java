//: innerclasses/Sequence.java
package generics; /* Added by Eclipse.py */
// Holds a sequence of Objects.

/* Exercise 15.04:
 (3) "Generify" innerclasses/Sequence.java.
 */

interface Selector<T> {
    boolean end();
    T current();
    void next();
}

class Sequence2<T> {
    private T[] items;
    private int next = 0;

    Sequence2(int size) {
        items = (T[])new Object[size];
    }

    public void add(T x) {
        if (next < items.length)
            items[next++] = x;
    }

    private class SequenceSelector implements Selector {
        private int i = 0;

        public boolean end() {
            return i == items.length;
        }

        public T current() {
            return items[i];
        }

        public void next() {
            if (i < items.length) i++;
        }
    }

    Selector selector() {
        return new SequenceSelector();
    }
}

public class Exercise1504 {
    public static void main(String[] args) {
        Sequence2<String> sequence = new Sequence2<>(10);

        for(int i = 0; i < 10; i++)
            sequence.add(Integer.toString(i));

        Selector selector = sequence.selector();

        while(!selector.end()) {
            System.out.print(selector.current() + " ");
            selector.next();
        }
    }
} /* Output:
0 1 2 3 4 5 6 7 8 9
*///:~
