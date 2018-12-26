package tmp;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

    static private List<Class> sort8(List<Class> classes) {
        return classes.stream().sorted(comparing(AnnotatedSort::priority)).collect(Collectors.toList());
    }

    static private List<Class> sort7(List<Class> classes) {
        Collections.sort(classes, new Comparator<Class>() {
            public int compare(Class c1, Class c2){
                return Integer.compare(priority(c1), priority(c2));
            }
        });
        return classes;
    }

    public static void main(String[] args) throws Exception {
        List<Class> classes = Arrays.asList(C.class, B.class, A.class);
        for (Class<?> clz : sort7(classes)) {
            System.out.println(clz);
        }
        for (Class<?> clz : sort8(classes)) {
            System.out.println(clz);
        }
    }
}
