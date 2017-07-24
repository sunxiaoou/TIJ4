//: containers/SlowMap.java
package containers; /* Added by Eclipse.py */
// A Map implemented with ArrayLists.

// import net.mindview.util.RandomGenerator;

import java.util.*;

// import static net.mindview.util.Print.*;

/* Exercise 17.36
 (5) Modify SlowMap so that instead of two ArrayLists, it holds a single
 ArrayList of MapEntry objects. Verify that the modified version works correctly. Using
 MapPerformance.java, test the speed of your new Map. Now change the put( ) method
 so that it performs a sort( ) after each pair is entered, and modify get( ) to use
 Collections.binarySearch( ) to look up the key. Compare the performance of the new
 version with the old ones.
 */


class SlowMap3<K extends Comparable, V> extends AbstractMap<K, V> {
  class MapEntry3<K extends Comparable, V> extends MapEntry<K, V> implements Comparable<MapEntry3<K, V>> {
    MapEntry3(K key, V value) { super(key, value); }
    @Override
    public int compareTo(MapEntry3 e) {
      return key.compareTo(e.getKey());
    }
  }

  List<MapEntry3<K, V>> entrys = new ArrayList<>();

  /*
  private MapEntry3<K, V> getEntry(K key) {
    for (MapEntry3<K, V> entry : entrys)
      if (entry.getKey().equals(key))
        return entry;
    return null;
  }
  */

  private MapEntry3<K, V> getEntry(K key) {
    int index = Collections.binarySearch(entrys, new MapEntry3<>(key, null));
    if (index < 0)
      return null;
    return entrys.get(index);
  }

  public V get(Object key) { // key is type Object, not K
    MapEntry3<K, V> entry = getEntry((K)key);
    if (entry != null)
      return entry.getValue();
    return null;
  }

  public V put(K key, V value) {
    V oldValue = null;
    MapEntry3<K, V> entry = getEntry(key);
    if (entry != null) {
      oldValue = entry.getValue();
      entry.setValue(value);
    }
    else {
      entrys.add(new MapEntry3<>(key, value));
      Collections.sort(entrys);
    }
    return oldValue;
  }

  class EntrySet extends AbstractSet<Map.Entry<K, V>> {
    private Iterator<MapEntry3<K, V>> ei = entrys.iterator();
    public int size() { return entrys.size(); }

    public Iterator<Map.Entry<K, V>> iterator() {
      return new Iterator<Map.Entry<K, V>>() {
        public boolean hasNext() {
          return ei.hasNext();
        }
        public MapEntry3<K, V> next() { return ei.next(); }
        public void remove() { ei.remove(); }
      };
    }
  }

  public Set<Map.Entry<K, V>> entrySet() { return new EntrySet(); }
}

public class Exercise1736 {
  static List<Test<Map<Integer,Integer>>> tests() {
    List<Test<Map<Integer,Integer>>> tests = new ArrayList<>();
    tests.add(new Test<Map<Integer,Integer>>("put") {
      int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          map.clear();
          for(int j = 0; j < size; j++)
            map.put(j, j);
        }
        return loops * size;
      }
    });
    tests.add(new Test<Map<Integer,Integer>>("get") {
      int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops;
        int span = tp.size * 2;
        for(int i = 0; i < loops; i++)
          for(int j = 0; j < span; j++)
            map.get(j);
        return loops * span;
      }
    });
    tests.add(new Test<Map<Integer,Integer>>("iterate") {
      int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops * 10;
        for(int i = 0; i < loops; i ++) {
          Iterator it = map.entrySet().iterator();
          while(it.hasNext())
            it.next();
        }
        return loops * map.size();
      }
    });
    return tests;
  }

  public static void main(String[] args) {
    /*
    Map<Integer, String> map = new SlowMap3<>();
    Random rand = new Random(47);
    RandomGenerator.String randstr = new RandomGenerator.String(4);
    for (int i = 0; i < 10; i ++) {
      map.put(rand.nextInt(100), randstr.next());
    }
    print(map);

    rand = new Random(47);
    for (int i = 0; i < 10; i ++) {
      int key = rand.nextInt(100);
      print(key + "=" + map.get(key));
    }
    */

    List<Test<Map<Integer,Integer>>> tests = tests();
    Tester.run(new HashMap<>(), tests);
    Tester.run(new SlowMap3<>(), tests);
  }
}
