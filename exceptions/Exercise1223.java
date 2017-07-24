//: exceptions/Cleanup.java
package exceptions; /* Added by Eclipse.py */


/* Exercise 12.23
 Add a class with a dispose( ) method to the previous exercise. Modify
 FailingConstructor so that the constructor creates one of these disposable objects as a
 member object, after which the constructor might throw an exception, after which it creates a
 second disposable member object. Write code to properly guard against failure, and in
 main( ) verify that all possible failure situations are covered.
 */

class NeedsCleanup3 extends NeedsCleanup {
    NeedsCleanup3() throws ConstructionException {
        if (id() == 1)
            throw new ConstructionException();
    }
}

class FailingConstructor2 {
    private NeedsCleanup nc;
    FailingConstructor2() {
        System.out.println("FailingConstructor()");
        while (true) {
            try {
                nc = new NeedsCleanup3();
                break;
            } catch (ConstructionException e) {
                e.printStackTrace(System.out);
            }
        }
    }
    void f() { System.out.println("nc(" + nc.id() + ")"); }
    void dispose() { nc.dispose(); }
}

public class Exercise1223 {
    public static void main(String[] args) {
        try {
            FailingConstructor2 fc = new FailingConstructor2();
            try {
                fc.f();
            } catch(Exception e) {
                System.out.println("Caught Exception in main");
                e.printStackTrace(System.out);
            } finally {
                fc.dispose();
            }
        } catch(Exception e) {
            System.out.println("construction failed");
            e.printStackTrace();
        }
    }
}