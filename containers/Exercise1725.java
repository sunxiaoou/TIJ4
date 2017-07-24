package containers; /* Added by Eclipse.py */


/* Exercise 17.25
 (6) Instead of using a Listlterator for each bucket, modify MapEntry so
 that it is a self-contained singly linked list (each MapEntry should have a forward link to the
 next MapEntry). Modify the rest of the code in SimpleHashMap.java so that this new
 approach works correctly.
 */


import java.util.*;

import static net.mindview.util.Print.*;

class LinkedMapEntry<K, V> extends MapEntry<K, V> {
  LinkedMapEntry next = null;
  LinkedMapEntry(K key, V value) { super(key, value); }
}

class LinkedSimpleMap<K, V> extends AbstractMap<K, V> {
  // Choose a prime number for the hash table
  // size, to achieve a uniform distribution:
  // static final int SIZE = 997;
  private static final int SIZE = 97;
  // You can't have a physical array of generics,
  // but you can upcast to one:
  @SuppressWarnings("unchecked")

  private LinkedList<LinkedMapEntry<K, V>>[] buckets = new LinkedList[SIZE];
  private LinkedMapEntry<K, V> head = new LinkedMapEntry<>(null, null);

  private LinkedMapEntry<K, V> previousEntry(LinkedMapEntry entry) {
    int row = Math.abs(entry.getKey().hashCode()) % SIZE;
    LinkedList<LinkedMapEntry<K, V>> bucket = buckets[row];
    if (bucket.size() > 1) {
      int column = bucket.indexOf(entry);
      return bucket.get(column - 1);
    }
    for (int i = row - 1; i >= 0; i --) {
      bucket = buckets[i];
      if (bucket != null && bucket.size() > 0)
        return bucket.get(bucket.size() - 1);
    }
    return head;
  }

  public V put(K key, V value) {
    V oldValue = null;
    int index = Math.abs(key.hashCode()) % SIZE;
    if(buckets[index] == null)
      buckets[index] = new LinkedList<>();
    LinkedList<LinkedMapEntry<K, V>> bucket = buckets[index];
    boolean found = false;
    for (LinkedMapEntry<K, V> iPair : bucket) {
      if (iPair.getKey().equals(key)) {
        oldValue = iPair.getValue();
        iPair.setValue(value); // Replace old with new
        found = true;
        break;
      }
    }
    if(! found) {
      LinkedMapEntry<K, V> pair = new LinkedMapEntry<>(key, value);
      buckets[index].add(pair);
      LinkedMapEntry<K, V> previous = previousEntry(pair);
      pair.next = previous.next;
      previous.next = pair;
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


  class EntrySet extends AbstractSet<Map.Entry<K, V>> {
    public int size() {
      int size = 0;
      for (LinkedList<LinkedMapEntry<K, V>> bucket : buckets) {
        if (bucket != null)
          size += bucket.size();
      }
      return size;
    }

    public Iterator<Map.Entry<K, V>> iterator() {
      return new Iterator<Map.Entry<K, V>>() {
        private LinkedMapEntry entry = head;

        public boolean hasNext() { return entry.next != null; }

        public MapEntry next() {
          if (entry.next == null)
            return null;
          entry = entry.next;
          return entry;
        }

        public void remove() {
          if (entry != head) {
            int index = Math.abs(entry.getKey().hashCode()) % SIZE;
            LinkedList<LinkedMapEntry<K, V>> bucket = buckets[index];
            LinkedMapEntry<K, V> previous = previousEntry(entry);
            previous.next = entry.next;
            bucket.remove(entry);
          }
        }
      };
    }
  }

  public Set<Map.Entry<K, V>> entrySet() { return new EntrySet(); }
}


public class Exercise1725 {
  public static void main(String[] args) {

    Exercise1715.count(new HashMap<>());
    Exercise1715.count(new LinkedSimpleMap<>());
    print();
    Maps.test(new HashMap<>());
    print();
    Maps.test(new LinkedSimpleMap<>());

/*
    Map<Integer, String> map = new LinkedSimpleMap<>();
    map.put(1, "hello");
    map.put(1, "world");

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