package typeinfo;

/**
 * Created by xixisun on 17-5-3.
 */

class BaseA {
    int i;
    BaseA() { i = 1; }
    BaseA(int i) { this.i = i; }
}

class BA extends BaseA {
    int j;
    // BA(int i) { super(3); this.i = i; }
    BA(int j) {
        // super();
        i ++;
        this.j = j;
    }
}


public class TestBA {
    public static void main(String args[]) {
        BA ba = new BA(5);
        System.out.println(ba.i + " " + ba.j);
        BaseA a = ba;
        System.out.println(a.i);
    }
}
