//: containers/Maps.java
package containers; /* Added by Eclipse.py */
// Things you can do with Maps.

/* Exercise 17.16
 (7) Apply the tests in Maps.java to SlowMap to verify that it works. Fix
 anything in SlowMap that doesn’t work correctly.
*/


import java.util.*;

import static net.mindview.util.Print.*;

class SlowMap2<K, V> extends SlowMap<K, V> {

  class MapEntry2 extends MapEntry<K, V> {
    MapEntry2(K key, V value) { super(key, value); }
    public void setKey(K key) { this.key = key; }
  }
  private MapEntry2 entry = new MapEntry2(null, null);

  class EntrySet extends AbstractSet<Map.Entry<K, V>> {
    private Iterator<K> ki;
    private Iterator<V> vi;

    EntrySet() {
      ki = keys.iterator();
      vi = values.iterator();
    }

    public int size() { return keys.size(); }
    public Iterator<Map.Entry<K, V>> iterator() {
      return new Iterator<Map.Entry<K, V>>() {
        public boolean hasNext() {
          return ki.hasNext();
        }
        public MapEntry2 next() {
          entry.setKey(ki.next());
          entry.setValue(vi.next());
          return entry;
        }
        public void remove() {
          ki.remove();
          vi.remove();
        }
      };
    }
  }

  public Set<Map.Entry<K, V>> entrySet() { return new EntrySet(); }
}


public class Exercise1716 {
  public static void main(String[] args) {
    Maps.test(new HashMap<>());
    print();
    Maps.test(new SlowMap2<>());
  }
}