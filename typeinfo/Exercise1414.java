//: typeinfo/RegisteredFactories.java
package typeinfo; /* Added by Eclipse.py */
// Registering Class Factories in the base class.

/*  Exercise 14.14
(4) A constructor is a kind of factory method. Modify
RegisteredFactories.java so that instead of using an explicit factory, the class object is
stored in the List, and newlnstance( ) is used to create each object.
 */

import java.util.*;

class Part2 {
    public String toString() {
        return getClass().getSimpleName();
    }
    public static final List<Class<? extends Part2>> types =
            Collections.unmodifiableList(Arrays.asList(
                    FuelFilter2.class, AirFilter2.class, CabinAirFilter2.class, OilFilter2.class,
                    FanBelt2.class, PowerSteeringBelt2.class, GeneratorBelt2.class));

    private static Random rand = new Random(47);
    public static Part2 createRandom() throws IllegalAccessException, InstantiationException {
        int n = rand.nextInt(types.size());
        return types.get(n).newInstance();
    }
}

class Filter2 extends Part2 {}

class FuelFilter2 extends Filter2 {}

class AirFilter2 extends Filter2 {}

class CabinAirFilter2 extends Filter2 {}

class OilFilter2 extends Filter2 {}

class Belt2 extends Part2 {}

class FanBelt2 extends Belt2 {}

class GeneratorBelt2 extends Belt2 {}

class PowerSteeringBelt2 extends Belt2 {}

public class Exercise1414 {
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++)
            try {
                System.out.println(Part2.createRandom());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
    }
}