//: net/mindview/util/CountingMapData.java
// Unlimited-length Map containing sample data.
package containers;

/* Exercise 17.05
 (3) Modify CountingMapData.java to fully implement the flyweight by
 adding a custom EntrySet class like the one in Countries.java.
 */


import java.util.*;

class CountingMapData extends AbstractMap<Integer,String> {
  private int size;
  private static String[] chars =
          "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
  public CountingMapData(int size) {
    if (size < 0) this.size = 0;
    this.size = size;
  }

  private static class Entry implements Map.Entry<Integer, String> {
    int index;
    Entry(int index) { this.index = index; }
    public boolean equals(Object o) { return Integer.valueOf(index).equals(o); }
    public Integer getKey() { return index; }
    public String getValue() {
      return chars[index % chars.length] + Integer.toString(index / chars.length);
    }
    public String setValue(String value) {
      throw new UnsupportedOperationException();
    }
    public int hashCode() {
      return Integer.valueOf(index).hashCode();
    }
  }

  static class EntrySet extends AbstractSet<Map.Entry<Integer, String>> {
    private int size;
    EntrySet(int size) { this.size = size < 0 ? 0 : size; }
    public int size() { return size; }
    public Iterator<Map.Entry<Integer, String>> iterator() {
      return new Iterator<Map.Entry<Integer, String>>() {
        private CountingMapData.Entry entry = new CountingMapData.Entry(-1);
        public boolean hasNext() {
          return entry.index < size - 1;
        }
        public Map.Entry<Integer, String> next() {
          entry.index++;
          return entry;
        }
        public void remove() {
          throw new UnsupportedOperationException();
        }
      };
    }
  }

  public Set<Map.Entry<Integer, String>> entrySet() {
    return new EntrySet(size);
  }
}

public class Exercise1705 {
  public static void main(String[] args) {
    System.out.println(new CountingMapData(60));
  }
}