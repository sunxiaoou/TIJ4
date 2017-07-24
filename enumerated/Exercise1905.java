//: control/VowelsAndConsonants.java
package enumerated; /* Added by Eclipse.py */
// Demonstrates the switch statement.

/* Exercise 19.05
 (4) Modify control/VowelsAndConsonants.java so that it uses three
 enum types: VOWEL, SOMETIMES_A_VOWEL, and CONSONANT. The enum
 constructor should take the various letters that describe that particular category. Hint: Use
 varargs, and remember that varargs automatically creates an array for you.
*/


import java.util.*;

import static net.mindview.util.Print.*;

enum TONE {
  VOWEL('a', 'e', 'i', 'o', 'u'),
  SOMETIMES_A_VOWEL('w', 'y'),
  CONSONANT('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'x', 'z');
  private Character[] letters;
  TONE(Character ... letters) { this.letters = letters; }
  public static TONE type(char c) {
    if (Arrays.asList(VOWEL.letters).contains(c))
      return VOWEL;
    if (Arrays.asList(SOMETIMES_A_VOWEL.letters).contains(c))
      return SOMETIMES_A_VOWEL;
    return CONSONANT;
  }
}

public class Exercise1905 {
  public static void main(String[] args) {
    Random rand = new Random(47);
    for(int i = 0; i < 100; i++) {
      int c = rand.nextInt(26) + 'a';
      printnb((char)c + ", " + c + ": ");
      print(TONE.type((char)c));
    }
  }
}