package generics;

/* Exercise 15.20
 (1) Create an interface with two methods, and a class that implements that
 interface and adds another method. In another class, create a generic method with an
 argument type that is bounded by the interface, and show that the methods in the interface
 are callable inside this generic method. In main( ), pass an instance of the implementing
 class to the generic method.
*/


import static net.mindview.util.Print.*;

interface TwoMethods {
    void method1();
    void method2();
}

class ThreeMethods implements TwoMethods {
    public void method1() { print("method1()"); }
    public void method2() { print("method2()"); }
    public void method3() { print("method3()"); }
}

public class Exercise1520 {
    static <T extends TwoMethods> void call(T obj) {
        obj.method1();
        obj.method2();
    }

    public static void main(String args[]) {
        call(new ThreeMethods());
    }
}