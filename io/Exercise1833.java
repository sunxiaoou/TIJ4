//: io/PreferencesDemo.java
package io; /* Added by Eclipse.py */


/* Exercise 18.33
 (2) Write a program that displays the current value of a directory called
 "base directory" and prompts you for a new value. Use the Preferences API to store the value.
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

import static net.mindview.util.Print.print;

public class Exercise1833 {
  public static void main(String[] args) throws Exception {
    Preferences prefs = Preferences.userNodeForPackage(Exercise1833.class);
    // int value = prefs.getInt("base directory", 0);
    String value = prefs.get("base directory", null);
    // System.out.print("Base directory value = " + value + "\nEnter new base directory value (integer): ");
    System.out.print("Base directory value = " + value + "\nEnter new base directory value (string): ");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      // value = Integer.parseInt(br.readLine());
      value = br.readLine();
    } catch (Exception e) {
      print(e);
      throw new RuntimeException(e);
    }
    // prefs.putInt("base directory", value);
    prefs.put("base directory", value);
  }
}