//: innerclasses/ClassInInterface.java
package innerclasses; /* Added by Eclipse.py */
// {main: ClassInInterface$Test}

public interface ClassInInterface {
  void howdy();
  class Test implements ClassInInterface {
    public void howdy() {
      System.out.println("Howdy!!");
    }
// exercise 10.21: call a method in interface
    private static void test(ClassInInterface t) { t.howdy();}
    public static void main(String[] args) {
      // new Test().howdy();
      test(new Test());
    }
  }
} /* Output:
Howdy!
*///:~
