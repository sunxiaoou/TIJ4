package tmp;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.util.Comparator.comparing;

@Target({TYPE,PARAMETER})
@Retention(RUNTIME)
@interface Priority {
    int value();
}

@Priority(1)
class A {}

@Priority(2)
class B {}

class C {}

public class AnnotatedSort {
    static private int priority(Class<?> clz) {
        return clz.isAnnotationPresent(Priority.class) ? clz.getAnnotation(Priority.class).value() : Integer.MAX_VALUE;
    }

    static private List<Class> sort(List<Class> classes) {
        return classes.stream().sorted(comparing(AnnotatedSort::priority)).collect(Collectors.toList());
    }

    public static void main(String[] args) throws Exception {
        List<Class> classes = Arrays.asList(C.class, B.class, A.class);
        for (Class<?> clz : sort(classes)) {
            System.out.println(clz);
        }
    }
}
