package generics; /* Added by Eclipse.py */

/* Exercise 15.25
(2) Create two interfaces and a class that implements both. Create two
generic methods, one whose argument parameter is bounded by the first interface and one
whose argument parameter is bounded by the second interface. Create an instance of the
class that implements both interfaces, and show that it can be used with both generic
methods.
 */

import static net.mindview.util.Print.*;

interface Interface1 {}
interface Interface2 {}
interface Interface3 {}

class ClassA implements Interface1, Interface2 {}

public class Exercise1525 {
    static <T extends Interface1> void method1(T t) { print(t + " method1()"); }
    static <T extends Interface2> void method2(T t) { print(t + " method2()"); }
    static <T extends Interface3> void method3(T t) { print(t + " method3()"); }
    public static void main(String args[]) {
        ClassA a = new ClassA();
        method1(a);
        method2(a);
        // method3(a);
    }
}