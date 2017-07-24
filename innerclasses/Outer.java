package innerclasses;

/**
 * Created by xixisun on 17-4-17.
 */
public class Outer {
    private String s = "outer string";

    class Inter {
        private int i = 42;
        public int value() { return i;}

        Outer outer() {
            return Outer.this;
        }

        String outer_str() {
            s = "outer string is changed";
            return s;
        }
    }

    public Inter inter() { return new Inter(); }

    public void print() {
        Inter i = inter();
        System.out.println(i.value());
    }
    public static void main(String [] args) {
        Outer outer = new Outer();
//      outer.print();
        Outer.Inter i = outer.inter();
// exercise 10.8: outer access inter's private member?
        System.out.println(i.i ++);
        System.out.println(i.value());
        System.out.println(i.outer().s);
// exercise 11.7: inter's method modify outer's member
        System.out.println(i.outer_str());
    }
}
