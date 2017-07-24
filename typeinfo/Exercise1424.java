//: typeinfo/RegisteredFactories.java
package typeinfo; /* Added by Eclipse.py */
// Registering Class Factories in the base class.

/*  Exercise 14.24
(4) Add Null Objects to RegisteredFactories.java.
 */

import net.mindview.util.Null;

import java.util.*;

class Part3 {
    public static class NullPart3 extends Part3 implements Null {}
    public static final Part3 NULL = new NullPart3();

    public String toString() {
        return getClass().getSimpleName();
    }

    public static final List<Class<? extends Part3>> types =
            Collections.unmodifiableList(Arrays.asList(
                    FuelFilter3.class, AirFilter3.class, CabinAirFilter3.class, OilFilter3.class,
                    FanBelt3.class, PowerSteeringBelt3.class, GeneratorBelt3.class));

    private static Random rand = new Random(47);
    public static Part3 createRandom() throws IllegalAccessException, InstantiationException {
        int n = rand.nextInt(types.size());
        return types.get(n).newInstance();
    }

}

class Filter3 extends Part3 {}

class FuelFilter3 extends Filter3 {}

class AirFilter3 extends Filter3 {}

class CabinAirFilter3 extends Filter3 {}

class OilFilter3 extends Filter3 {}

class Belt3 extends Part3 {}

class FanBelt3 extends Belt3 {}

class GeneratorBelt3 extends Belt3 {}

class PowerSteeringBelt3 extends Belt3 {}

public class Exercise1424 {
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++)
            try {
                System.out.println(Part3.createRandom());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
         System.out.println(Part3.NULL);
    }
}