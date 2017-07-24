//: containers/ListPerformance.java
package containers; /* Added by Eclipse.py */
// Demonstrates performance differences in Lists.
// {Args: 100 500} Small to keep build testing short

/* Exercise 17.30
 (3) Compare the performance of Collections.sort( ) between an
 ArrayList and a LinkedList.
 */

import net.mindview.util.*;

import java.util.*;

// import static net.mindview.util.Print.*;

public class Exercise1730 {
  static List<Test<List<Integer>>> tests = new ArrayList<>();

  static {
    tests.add(new Test<List<Integer>>("sort") {
      int test(List<Integer> list, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        RandomGenerator.Integer gen = new RandomGenerator.Integer(1000);
        for (int i = 0; i < loops; i++) {
          list.clear();
          list.addAll(Arrays.asList(Generated.array(Integer.class, gen, size)));
          // if (i == loops - 1)
          //  print(list);
          Collections.sort(list);
          // if (i == loops - 1)
          //  print(list);
        }
        return loops * size;
      }
    });
  }

  static class ListTester extends Tester<List<Integer>> {
    public ListTester(List<Integer> container, List<Test<List<Integer>>> tests) {
      super(container, tests);
    }

    // Fill to the appropriate size before each test:
    @Override
    protected List<Integer> initialize(int size) {
      container.clear();
      container.addAll(new CountingIntegerList(size));
      return container;
    }

    // Convenience method:
    public static void run(List<Integer> list, List<Test<List<Integer>>> tests) {
      new ListTester(list, tests).timedTest();
    }
  }

  public static void main(String[] args) {
    // Tester.defaultParams = TestParam.array(10, 5000, 100, 5000, 1000, 1000, 10000, 200);
    Tester.defaultParams = TestParam.array(10, 5000, 10, 50000, 10, 50000);
    if (args.length > 0)
      Tester.defaultParams = TestParam.array(args);
    ListTester.run(new ArrayList<>(), tests);
    ListTester.run(new LinkedList<>(), tests);
  }
}