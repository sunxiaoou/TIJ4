package containers;

/* Exercise 17.02
 (2) Produce a Map and a Set containing all the countries that begin with ‘A’.
 */


import java.util.*;

import net.mindview.util.Countries;

import static net.mindview.util.Print.print;

public class Exercise1702 {
  public static void main(String args[]) {
    Map<String, String> countries = new HashMap<>();
    for(int i = 0; i < Countries.DATA.length; i ++)
      if (Countries.DATA[i][0].charAt(0) == 'A')
        countries.put(Countries.DATA[i][0], Countries.DATA[i][1]);
    print("countries(" + countries.size() + ")");
    print(countries);
    print();

    Set<String> countries2 = countries.keySet();
    print("countries2(" + countries2.size() + ")");
    print(countries2);
  }
}
