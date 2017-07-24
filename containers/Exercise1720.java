//: holding/UniqueWordsAlphabetic.java
package containers; /* Added by Eclipse.py */


/* Exercise 17.15
 (1) Repeat Exercise 13 using a SlowMap.
*/

import java.util.*;

import static net.mindview.util.Print.*;

class MySimpleMap<K, V> extends AbstractMap<K, V> {
  // Choose a prime number for the hash table
  // size, to achieve a uniform distribution:
  // static final int SIZE = 997;
  static final int SIZE = 97;
  // You can't have a physical array of generics,
  // but you can upcast to one:
  @SuppressWarnings("unchecked")
  LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

  public V put(K key, V value) {
    V oldValue = null;
    int index = Math.abs(key.hashCode()) % SIZE;
    if(buckets[index] == null)
      buckets[index] = new LinkedList<>();
    LinkedList<MapEntry<K, V>> bucket = buckets[index];
    MapEntry<K, V> pair = new MapEntry<>(key, value);
    boolean found = false;
    ListIterator<MapEntry<K, V>> it = bucket.listIterator();
    while(it.hasNext()) {
      MapEntry<K, V> iPair = it.next();
      if(iPair.getKey().equals(key)) {
        oldValue = iPair.getValue();
        it.set(pair); // Replace old with new
        found = true;
        break;
      }
    }
    if(!found) {
      if (getClass() == MySimpleMap.class && buckets[index].size() != 0)
        print("Key(" + key + ") conflicts at bucket(" + index + ") with size(" + buckets[index].size() + ")");
      buckets[index].add(pair);
    }
    return oldValue;
  }

  public V get(Object key) {
    int index = Math.abs(key.hashCode()) % SIZE;
    if(buckets[index] == null)
      return null;
    for(MapEntry<K,V> iPair : buckets[index])
      if(iPair.getKey().equals(key))
        return iPair.getValue();
    return null;
  }

  public Set<Map.Entry<K, V>> entrySet() {
    Set<Map.Entry<K, V>> set= new HashSet<>();
    for(LinkedList<MapEntry<K, V>> bucket : buckets) {
      if(bucket == null) continue;
      for(MapEntry<K, V> mpair : bucket)
        set.add(mpair);
    }
    return set;
  }
}

public class Exercise1720 {
  public static void main(String[] args) {
    Exercise1715.count(new HashMap<>());
    print();
    Exercise1715.count(new MySimpleMap<>());
  }
}