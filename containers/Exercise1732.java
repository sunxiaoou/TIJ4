package containers; /* Added by Eclipse.py */

/* Exercise17.32
 (2) Repeat the previous exercise for a container of int, and compare the
 performance to an ArrayList<Integer>. In your performance comparison, include the
 process of incrementing each object in the container.
*/


import net.mindview.util.*;

import java.lang.reflect.Array;
import java.util.*;


class GenericArray<E> extends AbstractList<E> {
    private int size = 0;
    private E[] array;
    private Class<E> type;
    GenericArray(Class<E> type, int capacity) {
        this.type = type;
        array = (E[]) Array.newInstance(type, capacity);
    }

    @Override
    public int size() { return size; }
    public void clear() {
        size = 0;
        Arrays.fill(array, null);
    }

    public boolean add(E e) {
        if (size == array.length) {
            E[] tmp = (E[]) Array.newInstance(type, array.length + 100);
            System.arraycopy(array, 0, tmp, 0, array.length);
            array = tmp;
        }
        array[size ++] = e;
        return true;
    }
    public E get(int index) { return array[index]; }
}

public class Exercise1732 {
    static Random rand = new Random();
    static int reps = 1000;
    static RandomGenerator.String genstr = new RandomGenerator.String(5);
    static RandomGenerator.Integer genint = new RandomGenerator.Integer(1000);

    static <E> List<Test<List<E>>> tests(Generator<E> gen) {
        List<Test<List<E>>> tests = new ArrayList<>();
        tests.add(new Test<List<E>>("add") {
            int test(List<E> list, TestParam tp) {
                int loops = tp.loops;
                int listSize = tp.size;
                for (int i = 0; i < loops; i++) {
                    list.clear();
                    for (int j = 0; j < listSize; j++)
                        list.add(gen.next());
                }
                return loops * listSize;
            }
        });
        tests.add(new Test<List<E>>("get") {
            int test(List<E> list, TestParam tp) {
                int loops = tp.loops * reps;
                int listSize = list.size();
                for (int i = 0; i < loops; i++)
                    list.get(rand.nextInt(listSize));
                return loops;
            }
        });
        return tests;
    }

    public static void main(String[] args) {
        if (args.length > 0)
            Tester.defaultParams = TestParam.array(args);

        List<Test<List<String>>> stringTests = tests(genstr);
        Tester<List<String>> stringArray =
                new Tester<List<String>>(null, stringTests) {
                    @Override protected
                    List<String> initialize(int size) {
                        List<String> container = new GenericArray<>(String.class, size);
                        for (int i = 0; i < size; i ++)
                            container.add(genstr.next());
                        return container;
                    }
                };
        stringArray.setHeadline("Generic Array String");
        stringArray.timedTest();

        List<Test<List<Integer>>> integerTests = tests(genint);
        Tester<List<Integer>> integerArray =
                new Tester<List<Integer>>(null, integerTests) {
                    @Override protected
                    List<Integer> initialize(int size) {
                        List<Integer> container = new GenericArray<>(Integer.class, size);
                        for (int i = 0; i < size; i ++)
                            container.add(genint.next());
                        return container;
                    }
                };
        integerArray.setHeadline("Generic Array Integer");
        integerArray.timedTest();
    }
}