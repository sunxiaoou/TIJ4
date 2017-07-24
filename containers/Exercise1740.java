package containers; /* Added by Eclipse.py */


/* Exercise 17.40
 (5) Create a class containing two String objects and make it Comparable
 so that the comparison only cares about the first String. Fill an array and an ArrayList with
 objects of your class, using the RandomGenerator generator. Demonstrate that sorting
 works properly. Now make a Comparator that only cares about the second String, and
 demonstrate that sorting works properly. Also perform a binary search using your
 Comparator.
 */

/* Exercise 17.42
 (2) Modify Exercise 40 so that an alphabetic sort is used.
 */

import net.mindview.util.RandomGenerator;

import java.util.*;

import static net.mindview.util.Print.*;

class TwoString implements Comparable<TwoString> {
    private String first;
    private String second;
    TwoString(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public int compareTo(TwoString e) {
    //    return first.compareTo(e.first);
        return first.compareToIgnoreCase(e.first);
    }

    static class SecondComparator implements Comparator<TwoString> {
        @Override
        public int compare(TwoString o1, TwoString o2) {
        //    return o1.second.compareTo(o2.second);
            return o1.second.compareToIgnoreCase(o2.second);
        }
    }

    @Override
    public String toString() { return first + ":" + second; }
}

public class Exercise1740 {
    public static void main(String[] args) {
        List<TwoString> strings = new ArrayList<>();
        RandomGenerator.String randStr = new RandomGenerator.String(4);
        for (int i = 0; i < 10; i ++)
            strings.add(new TwoString(randStr.next(), randStr.next()));
        print(strings);

        Collections.sort(strings);
        print(strings);
        TwoString s = strings.get(5);
        int i = Collections.binarySearch(strings, s);
        print(i + ":" + s);


        Collections.sort(strings, new TwoString.SecondComparator());
        print(strings);
        s = strings.get(5);
        i = Collections.binarySearch(strings, s, new TwoString.SecondComparator());
        print(i + ":" + s);
    }
}