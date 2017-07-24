package innerclasses;

/**
 * Created by xixisun on 17-4-17.
 */

interface Inter {
    int value();
    String outer_str();
}

public class Outer3 {
    private String s = "outer string";

    public Inter inter() {
        return new Inter() {
            private int i = 42;
            public int value() { return i;}

            public String outer_str() {
                s = s + " is changed";
                return s;
            }
        };
    }

    public static void main(String [] args) {
        Outer3 outer = new Outer3();
        Inter i = outer.inter();
        System.out.println(i.value());
// exercise 10.13: outer cannot access inter's private member
//      System.out.println(i.i);
        System.out.println(outer.s);
// exercise 10.12: inter's method modify outer's member
        System.out.println(i.outer_str());
    }
}
