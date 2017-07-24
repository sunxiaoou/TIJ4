package containers; /* Added by Eclipse.py */

/* Exercise17.31
 (5) Create a container that encapsulates an array of String, and that only
 allows adding Strings and getting Strings, so that there are no casting issues during use. If
 the internal array isn’t big enough for the next add, your container should automatically
 resize it. In main( ), compare the performance of your container with an
 ArrayList<String>.
*/

import net.mindview.util.*;

import java.util.*;

class StringArray extends AbstractList<String> {
    private int index = 0;
    private String[] array;
    StringArray(int capacity) {
        array = new String[capacity];
    }

    @Override
    public int size() { return index; }
    public void clear() {
        index = 0;
        Arrays.fill(array, null);
    }
    public boolean add(String s) {
        if (index == array.length) {
            String[] tmp = new String[array.length + 100];
            System.arraycopy(array, 0, tmp, 0, array.length);
            array = tmp;
        }
        array[index ++] = s;
        return true;
    }

    public String get(int index) { return array[index]; }
}

public class Exercise1731 {
    static Random rand = new Random();
    static int reps = 1000;
    static List<Test<List<String>>> tests = new ArrayList<>();
    static RandomGenerator.String gen = new RandomGenerator.String(5);

    static {
        tests.add(new Test<List<String>>("add") {
            int test(List<String> list, TestParam tp) {
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
        tests.add(new Test<List<String>>("get") {
            int test(List<String> list, TestParam tp) {
                int loops = tp.loops * reps;
                int listSize = list.size();
                for (int i = 0; i < loops; i++)
                    list.get(rand.nextInt(listSize));
                return loops;
            }
        });
    }

    public static void main(String[] args) {
        if (args.length > 0)
            Tester.defaultParams = TestParam.array(args);

        Tester<List<String>> stringArray =
                new Tester<List<String>>(null, tests) {
                    @Override protected
                    List<String> initialize(int size) {
                        List<String> container = new StringArray(size);
                        for (int i = 0; i < size; i ++)
                            container.add(gen.next());
                        return container;
                    }
                };
        stringArray.setHeadline("String Array");
        stringArray.timedTest();

        Tester<List<String>> arrayList =
                new Tester<List<String>>(new ArrayList<>(), tests) {
                    @Override
                    protected List<String> initialize(int size) {
                        container.clear();
                        container.addAll(Arrays.asList(Generated.array(String.class, gen, size)));
                        return container;
                    }
                };
        arrayList.setHeadline("Array List");
        arrayList.timedTest();
    }
}