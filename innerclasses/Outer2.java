package innerclasses;

/**
 * Created by xixisun on 17-4-17.
 */

class Outer_2 {
    class Inter {
        private int i = 42;

        public int value() {
            return i;
        }
    }
}

public class Outer2 {
    private Outer_2 o2 = new Outer_2();

    public static void main(String [] args) {
        Outer2 outer = new Outer2();

        Outer_2.Inter o2i = outer.o2.new Inter();

        System.out.println(o2i.value());
    }
}
