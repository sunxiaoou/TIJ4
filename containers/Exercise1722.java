//: holding/UniqueWordsAlphabetic.java
package containers; /* Added by Eclipse.py */


/* Exercise 17.22
 (4) Implement the clear( ) and remove( ) methods for
 SimpleHashMap.
 */

import java.util.*;

import static net.mindview.util.Print.*;

class MySimpleMap2<K, V> extends MySimpleMap<K, V> {
  class EntrySet extends AbstractSet<Map.Entry<K, V>> {
    public int size() {
      int size = 0;
      for (LinkedList<MapEntry<K, V>> bucket : buckets) {
        if (bucket != null)
          size += bucket.size();
      }
      return size;
    }

    public Iterator<Map.Entry<K, V>> iterator() {
      return new Iterator<Map.Entry<K, V>>() {
        private int[] idx = {-1, -1};   // { row, column }
        private LinkedList<MapEntry<K, V>> bucket;
        private ListIterator<MapEntry<K, V>> it;

        private int[] nextIdx() {
          int[] next;
          if (idx[0] != -1) {
            bucket = buckets[idx[0]];
            if (idx[1] < bucket.size() - 1) {
              next = new int[2];
              next[0] = idx[0];
              next[1] = idx[1] + 1;
              return next;
            }
          }
          for (int i = idx[0] + 1; i < SIZE; i++) {
            bucket = buckets[i];
            if (bucket != null && bucket.size() > 0) {
              next = new int[2];
              next[0] = i;
              next[1] = 0;
              return next;
            }
          }
          return null;
        }

        public boolean hasNext() { return nextIdx() != null; }

        public MapEntry next() {
          int[] next = nextIdx();
          if (next == null)
            return null;
          idx = next;
          bucket = buckets[idx[0]];
          it = bucket.listIterator(idx[1]);
          return it.next();
        }

        public void remove() {
          if (idx[0] != -1) {
            bucket = buckets[idx[0]];
            it = bucket.listIterator(idx[1]);
            if (it.hasNext()) {
              it.next();
              it.remove();
            }
          }
        }
      };
    }
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() { return new EntrySet(); }
}

public class Exercise1722 {
  public static void main(String[] args) {

    Exercise1715.count(new HashMap<>());
    Exercise1715.count(new MySimpleMap2<>());
    print();
    Maps.test(new HashMap<>());
    print();
    Maps.test(new MySimpleMap2<>());

/*
    Map<Integer, String> map = new MySimpleMap2<>();
    map.put(1, "hello");

    Set<Map.Entry<Integer, String>> set = map.entrySet();
    Iterator<Map.Entry<Integer, String>> it = set.iterator();
    print(it.hasNext());
    print(it.next());
    print(it.hasNext());
    print(it.next());

    print(map);

    map.remove(1);
    print(map);
*/
  }
}