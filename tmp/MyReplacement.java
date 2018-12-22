//: strings/TheReplacements.java
package tmp; /* Added by Eclipse.py */

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyReplacement {

  public static void main(String[] args) throws Exception {
    List<String> fileNames = Arrays.asList (
            "assembly_compat_standalone_jar_compat12_13_ear_client",
            "assembly_compat_standalone_jar_compat12_14_ear_client",
            "assembly_compat_standalone_jar_compat12_50_ear_client",
            "assembly_compat_standalone_jar_compat13_14_ear_client",
            "assembly_compat_standalone_jar_compat13_50_ear_client",
            "assembly_compat_standalone_jar_compat14_50_ear_client",
            "assembly_standalone_jar_ear_client"
    );

    for (String fileName : fileNames) {
      Matcher m = Pattern.
              compile("(assembly_compat_standalone_jar_compat\\d\\d_\\d\\d|assembly_standalone_jar)_ear_client").
              matcher(fileName);
      if (m.find()) {
        fileName = m.group(1) + "_client";  // comment "ear" out
        System.out.println(fileName);
      }
    }
  }
}