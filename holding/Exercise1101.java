package holding;

/* Exercise 11.01:
 Create a new class called Gerbil with an int gerbilNumber that’s
 initialized in the constructor. Give it a method called hop( ) that displays which gerbil
 number this is, and that it’s hopping. Create an ArrayList and add Gerbil objects to the
 List. Now use the get( ) method to move through the List and call hop( ) for each Gerbil
*/

/* Exercise 11.08:
 Modify Exercise l so it uses an Iterator to move through the List while
 calling hop( ).
*/


import java.util.*;

class Gerbil {
    private static long counter;
    private final long id = counter++;
    public void hop() {
        System.out.println("gerbil_" + id + ": hop()");
    }
}

public class Exercise1101 {
    public static void main(String[] args) {
        List<Gerbil> gerbils = new ArrayList<>();

        for (int i = 0; i < 3; i ++) {
            gerbils.add(new Gerbil());
        }

        /*
        for (Gerbil g : gerbils) {
            g.hop();
        }
        */

// Exercise 11.08: use Iterator
        Iterator<Gerbil> it = gerbils.iterator();

        while (it.hasNext()) {
            it.next().hop();
        }
    }
}
