//: containers/CountedString.java
package containers; /* Added by Eclipse.py */
// Creating a good hashCode().

/* Exercise 17.27
 (3) Modify the hashCode( ) in CountedString.java by removing the
 combination with id, and demonstrate that CountedString still works as a key. What is the
 problem with this approach?
 */


import java.util.*;

import static net.mindview.util.Print.*;

public class Exercise1727 {
  private static List<String> created =
    new ArrayList<String>();
  private String s;
  private int id = 0;
  public Exercise1727(String str) {
    s = str;
    created.add(s);
    // id is the total number of instances
    // of this string in use by CountedString:
    for(String s2 : created)
      if(s2.equals(s))
        id++;
  }
  public String toString() {
    return "String: " + s + " id: " + id +
      " hashCode(): " + hashCode();
  }
  public int hashCode() {
    // The very simple approach:
    // return s.hashCode() * id;
    // Using Joshua Bloch's recipe:
    int result = 17;
    result = 37 * result + s.hashCode();
    // result = 37 * result + id;
    return result;
  }
  public boolean equals(Object o) {
    return o instanceof Exercise1727 &&
      s.equals(((Exercise1727)o).s) &&
      id == ((Exercise1727)o).id;
  }
  public static void main(String[] args) {
    Map<Exercise1727,Integer> map =
      new HashMap<Exercise1727,Integer>();
    Exercise1727[] cs = new Exercise1727[5];
    for(int i = 0; i < cs.length; i++) {
      cs[i] = new Exercise1727("hi");
      map.put(cs[i], i); // Autobox int -> Integer
    }
    print(map);
    for(Exercise1727 cstring : cs) {
      print("Looking up " + cstring);
      print(map.get(cstring));
    }
  }
}
