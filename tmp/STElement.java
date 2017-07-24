package tmp;

import static net.mindview.util.Print.*;

/**
 * Created by xixisun on 17-7-6.
 */

public class STElement {
    private static void functionA() {
        StringBuilder sb = new StringBuilder("From CountryListener: ").append(System.lineSeparator());

        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            print(ste.getClassName());
            if (ste.getClassName().startsWith("tmp")) {
                sb.append("    ").append(ste.toString()).append(System.lineSeparator());
            }
        }

        print(sb.toString());
    }

    public static void main(String[] args) {
        functionA();
    }
}
