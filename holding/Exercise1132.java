//: holding/NonCollectionSequence.java
package holding; /* Added by Eclipse.py */

/* Exercise 11.32
 Following the example of MultilterableClass, add reversed( ) and
 randomized( ) methods to NonCollectionSequence.java, as well as making
 NonCollectionSequence implement Iterable, and show that all the approaches work in
 foreach statements.
*/

import typeinfo.pets.Pet;

import java.util.*;

class NonCollectionSequence2 extends PetSequence implements Iterable<Pet> {
  public Iterator<Pet> iterator() {
    return new Iterator<Pet>() {
      private int index = 0;
      public boolean hasNext() {
        return index < pets.length;
      }
      public Pet next() { return pets[index++]; }
      public void remove() { // Not implemented
        throw new UnsupportedOperationException();
      }
    };
  }

  public Iterable<Pet> reversed() {
    return new Iterable<Pet>() {
      public Iterator<Pet> iterator() {
        return new Iterator<Pet>() {
          int current = pets.length - 1;
          public boolean hasNext() { return current > -1; }
          public Pet next() { return pets[current--]; }
          public void remove() { // Not implemented
            throw new UnsupportedOperationException();
          }
        };
      }
    };
  }

  public Iterable<Pet> randomized() {
    return new Iterable<Pet>() {
      public Iterator<Pet> iterator() {
        List<Pet> shuffled = new ArrayList<>(Arrays.asList(pets));
        Collections.shuffle(shuffled, new Random(47));
        return shuffled.iterator();
      }
    };
  }
}

public class Exercise1132 {
  public static void main(String[] args) {
    NonCollectionSequence2 nc = new NonCollectionSequence2();

    InterfaceVsIterator.display(nc.iterator());
    InterfaceVsIterator.display(nc.reversed().iterator());
    InterfaceVsIterator.display(nc.randomized().iterator());

    for (Pet pet : nc)
      System.out.print(pet + " ");
    System.out.println();

    for (Pet pet : nc.reversed())
      System.out.print(pet + " ");
    System.out.println();

    for (Pet pet : nc.randomized())
      System.out.print(pet + " ");
    System.out.println();
  }
}