package holding;

import net.mindview.util.Generator;
import static net.mindview.util.Print.print;
import java.util.*;

/* Exercise 11.04
 Create a generator class that produces character names (as String
 objects) from your favorite movie (you can use Snow White or Star Wars as a fallback) each
 time you call next( ), and loops around to the beginning of the character list when it runs out
 of names. Use this generator to fill an array, an ArrayList, a LinkedList, a HashSet, a
 LinkedHashSet, and a TreeSet, then print each container.
*/

// interface Generator<T> { T next(); }

class Movies {
    static Collection fill(Collection<String> collection, Generator<String> generator) {
        for (int i = 0; i < 5; i++)
            collection.add(generator.next());
        return collection;
    }

    public Generator<String> generator() {
        return new Generator<String>() {
            private String[] movies = {"White Snow", "Star War", "Frozen"};
            private int i = 0;

            public String next() {
                if (i == movies.length) i = 0;
                return movies[i++];
            }
        };
    }
}

public class Exercise1104 {
    public static void main(String[] args) {
        Movies m = new Movies();
        Generator<String> g = m.generator();

        print(Movies.fill(new ArrayList<>(), g));
        print(Movies.fill(new LinkedList<String>(), g));
        print(Movies.fill(new HashSet<String>(), g));
        print(Movies.fill(new TreeSet<String>(), g));
        print(Movies.fill(new LinkedHashSet<String>(), g));
    }
}
