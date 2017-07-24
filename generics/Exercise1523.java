//: generics/FactoryConstraint.java
package generics; /* Added by Eclipse.py */

/* Exercise 15.23
 (1) Modify FactoryConstraint.java so that create( ) takes an
 argument.
*/


interface FactoryI2<T, U> {
    T create(U u);
}

class Foo22<T, U> {
    private T x;
    public <F extends FactoryI2<T, U>> Foo22(F factory, U u) {
        x = factory.create(u);
    }
    // ...
}

class IntegerFactory2 implements FactoryI2<Integer, Integer> {
    public Integer create(Integer i) { return i; }
}

class Widget2 {
    private String s;
    Widget2(String s) { this.s = s; }
    public static class Factory implements FactoryI2<Widget2, String> {
        public Widget2 create(String s) {
            return new Widget2(s);
        }
    }
}

public class Exercise1523 {
    public static void main(String[] args) {
        new Foo22<>(new IntegerFactory2(), 10);
        new Foo22<>(new Widget2.Factory(), "Widget2");
    }
} ///:~
