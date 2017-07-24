//: containers/ListPerformance.java
package containers; /* Added by Eclipse.py */
// Demonstrates performance differences in Lists.
// {Args: 100 500} Small to keep build testing short

/* Exercise 17.29
 (2) Modify ListPerformance.java so that the Lists hold String objects
 instead of Strings. Use a Generator from the Arrays chapter to create test values.
 */


import net.mindview.util.*;

import java.util.*;

public class Exercise1729 {
  static Random rand = new Random();
  static int reps = 1000;
  static List<Test<List<String>>> tests = new ArrayList<>();
  static List<Test<LinkedList<String>>> qTests = new ArrayList<>();
  static RandomGenerator.String gen = new RandomGenerator.String(5);
  static {
    tests.add(new Test<List<String>>("add") {
      int test(List<String> list, TestParam tp) {
        int loops = tp.loops;
        int listSize = tp.size;
        for(int i = 0; i < loops; i++) {
          list.clear();
          for(int j = 0; j < listSize; j++)
            list.add(gen.next());
        }
        return loops * listSize;
      }
    });
    tests.add(new Test<List<String>>("get") {
      int test(List<String> list, TestParam tp) {
        int loops = tp.loops * reps;
        int listSize = list.size();
        for(int i = 0; i < loops; i++)
          list.get(rand.nextInt(listSize));
        return loops;
      }
    });
    tests.add(new Test<List<String>>("set") {
      int test(List<String> list, TestParam tp) {
        int loops = tp.loops * reps;
        int listSize = list.size();
        for(int i = 0; i < loops; i++)
          list.set(rand.nextInt(listSize), gen.next());
        return loops;
      }
    });
    tests.add(new Test<List<String>>("iteradd") {
      int test(List<String> list, TestParam tp) {
        final int LOOPS = 1000000;
        int half = list.size() / 2;
        ListIterator<String> it = list.listIterator(half);
        for(int i = 0; i < LOOPS; i++)
          it.add(gen.next());
        return LOOPS;
      }
    });
    tests.add(new Test<List<String>>("insert") {
      int test(List<String> list, TestParam tp) {
        int loops = tp.loops;
        for(int i = 0; i < loops; i++)
          list.add(5, gen.next()); // Minimize random-access cost
        return loops;
      }
    });
    tests.add(new Test<List<String>>("remove") {
      int test(List<String> list, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          list.clear();
          list.addAll(Arrays.asList(Generated.array(String.class, gen, size)));
          while(list.size() > 5)
            list.remove(5); // Minimize random-access cost
        }
        return loops * size;
      }
    });
    // Tests for queue behavior:
    qTests.add(new Test<LinkedList<String>>("addFirst") {
      int test(LinkedList<String> list, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          list.clear();
          for(int j = 0; j < size; j++)
            list.addFirst(gen.next());
        }
        return loops * size;
      }
    });
    qTests.add(new Test<LinkedList<String>>("addLast") {
      int test(LinkedList<String> list, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          list.clear();
          for(int j = 0; j < size; j++)
            list.addLast(gen.next());
        }
        return loops * size;
      }
    });
    qTests.add(
      new Test<LinkedList<String>>("rmFirst") {
        int test(LinkedList<String> list, TestParam tp) {
          int loops = tp.loops;
          int size = tp.size;
          for(int i = 0; i < loops; i++) {
            list.clear();
            list.addAll(Arrays.asList(Generated.array(String.class, gen, size)));
            while(list.size() > 0)
              list.removeFirst();
          }
          return loops * size;
        }
      });
    qTests.add(new Test<LinkedList<String>>("rmLast") {
      int test(LinkedList<String> list, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          list.clear();
          list.addAll(Arrays.asList(Generated.array(String.class, gen, size)));
          while(list.size() > 0)
            list.removeLast();
        }
        return loops * size;
      }
    });
  }
  static class ListTester extends Tester<List<String>> {
    public ListTester(List<String> container,
        List<Test<List<String>>> tests) {
      super(container, tests);
    }
    // Fill to the appropriate size before each test:
    @Override protected List<String> initialize(int size){
      container.clear();
      container.addAll(Arrays.asList(Generated.array(String.class, gen, size)));
      return container;
    }
    // Convenience method:
    public static void run(List<String> list,
        List<Test<List<String>>> tests) {
      new ListTester(list, tests).timedTest();
    }
  }
  public static void main(String[] args) {
    // Can only do these two tests on an array:
    Tester<List<String>> arrayTest =
      new Tester<List<String>>(null, tests.subList(1, 3)){
        // This will be called before each test. It
        // produces a non-resizeable array-backed list:
        @Override protected
        List<String> initialize(int size) {
          String[] ia = Generated.array(String.class, gen, size);
          return Arrays.asList(ia);
        }
      };
    arrayTest.setHeadline("Array as List");
    arrayTest.timedTest();
    Tester.defaultParams= TestParam.array(10, 5000, 100, 5000, 1000, 1000, 10000, 200);
    if(args.length > 0)
      Tester.defaultParams = TestParam.array(args);
    ListTester.run(new ArrayList<>(), tests);
    ListTester.run(new LinkedList<>(), tests);
    ListTester.run(new Vector<>(), tests);
    Tester.fieldWidth = 12;
    Tester<LinkedList<String>> qTest = new Tester<>(new LinkedList<>(), qTests);
    qTest.setHeadline("Queue tests");
    qTest.timedTest();
  }
}
