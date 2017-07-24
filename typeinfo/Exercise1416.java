//: generics/coffee/Coffee.java
package typeinfo;

/* Exercise 14.16
(4) Modify the Coffee hierarchy in the Generics chapter to use Registered
Factories.
 */

import typeinfo.factory.Factory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Coffee {
    private static long counter = 0;
    private final long id = counter++;

    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }

    static List<Factory<? extends Coffee>> factories = new ArrayList<>();

    static {
        // Collections.addAll() gives an "unchecked generic
        // array creation ... for varargs parameter" warning.
        factories.add(new Americano.Factory());
        factories.add(new Breve.Factory());
        factories.add(new Cappuccino.Factory());
        factories.add(new Latte.Factory());
        factories.add(new Mocha.Factory());
    }

    private static Random rand = new Random(47);

    public static Coffee createRandom() {
        int n = rand.nextInt(factories.size());
        return factories.get(n).create();
    }
}

class Americano extends Coffee {
    public static class Factory implements typeinfo.factory.Factory<Americano> {
        public Americano create() { return new Americano(); }
    }
}

class Breve extends Coffee {
    public static class Factory implements typeinfo.factory.Factory<Breve> {
        public Breve create() { return new Breve(); }
    }
}

class Cappuccino extends Coffee {
    public static class Factory implements typeinfo.factory.Factory<Cappuccino> {
        public Cappuccino create() { return new Cappuccino(); }
    }
}

class Latte extends Coffee {
    public static class Factory implements typeinfo.factory.Factory<Latte> {
        public Latte create() { return new Latte(); }
    }
}

class Mocha extends Coffee {
    public static class Factory implements typeinfo.factory.Factory<Mocha> {
        public Mocha create() { return new Mocha(); }
    }
}

public class Exercise1416 {
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++)
            System.out.println(Coffee.createRandom());
    }
}