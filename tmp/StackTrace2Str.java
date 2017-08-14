package tmp;

import static net.mindview.util.Print.print;

/**
 * Created by xixisun on 17-7-6.
 */

public class StackTrace2Str {
    StackTrace2Str(Exception e) {
        StringBuilder sb = new StringBuilder(e.toString()).append(System.lineSeparator());

        for (StackTraceElement ste : e.getStackTrace()) {
            // print(ste.getClassName());
            // if (ste.getClassName().startsWith("tmp")) {
                sb.append("    ").append(ste.toString()).append(System.lineSeparator());
            // }
        }

        print(sb.toString());
    }

    public static void main(String[] args)
    {
        try {
            throw new NullPointerException();
        } catch (Exception e) {
            new StackTrace2Str(e);
        }
    }
}
