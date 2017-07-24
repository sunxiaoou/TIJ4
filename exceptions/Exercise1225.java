//: exceptions/Cleanup.java
package exceptions; /* Added by Eclipse.py */


/* Exercise 12.25
 Create a three-level hierarchy of exceptions. Now create a base-class A
 with a method that throws an exception at the base of your hierarchy. Inherit B from A and
 override the method so it throws an exception at level two of your hierarchy. Repeat by
 inheriting class C from B. In main( ), create a C and upcast it to A, then call the method.
 */

class ExceptionA extends Exception {}
class ExceptionB extends ExceptionA {}
class ExceptionC extends ExceptionB {}

class ClassA {
    void f() throws ExceptionA { throw new ExceptionA(); }
};

class ClassB extends ClassA {
    void f() throws ExceptionB { throw new ExceptionB(); }
};

class ClassC extends ClassB {
    void f() throws ExceptionC { throw new ExceptionC(); }
};


public class Exercise1225 {
    public static void main(String[] args) {
        ClassA c = new ClassC();
        try {
            ((ClassA)c).f();
        } catch (ExceptionA e) {
            e.printStackTrace();
        }
    }
}