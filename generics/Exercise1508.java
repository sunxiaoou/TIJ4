package generics; /* Added by Eclipse.py */

/* Exercise 15.08
 (2) Following the form of the Coffee example, create a hierarchy of
 StoryCharacters from your favorite movie, dividing them into GoodGuys and BadGuys.
 Create a generator for StoryCharacters, following the form of CoffeeGenerator.
 */

import java.util.Iterator;
import java.util.*;
import net.mindview.util.*;

class StoryCharacter {
    public String toString() {
        return getClass().getSimpleName();
    }
}

class GoodGuy extends StoryCharacter {}
class GoodType1 extends GoodGuy {}
class GoodType2 extends GoodGuy {}

class BadGuy extends StoryCharacter {}
class BadType1 extends BadGuy {}
class BadType2 extends BadGuy {}
class BadType3 extends BadGuy {}

class CharacterGenerator implements Generator<StoryCharacter>, Iterable<StoryCharacter> {
    private Class[] types = { GoodType1.class, GoodType2.class,
            BadType1.class, BadType2.class, BadType3.class };
    private static Random rand = new Random(47);
    public CharacterGenerator() {}
    // For iteration:
    private int size = 0;
    CharacterGenerator(int sz) { size = sz; }
    public StoryCharacter next() {
        try {
            return (StoryCharacter) types[rand.nextInt(types.length)].newInstance();
            // Report programmer errors at run time:
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Iterator<StoryCharacter> iterator() {
        return new Iterator<StoryCharacter>() {
            int n = size;
            public boolean hasNext() { return n > 0; }
            public StoryCharacter next() {
                n --;
                return CharacterGenerator.this.next();
            }
            public void remove() { // Not implemented
                throw new UnsupportedOperationException();
            }
        };
    }
}

public class Exercise1508 {
    public static void main(String[] args) {
        CharacterGenerator gen = new CharacterGenerator();
        for(int i = 0; i < 5; i++)
            System.out.println(gen.next());
        for(StoryCharacter i : new CharacterGenerator(5))
            System.out.print(i + " ");
    }
}