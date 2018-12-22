package tmp;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class FileMD5 {
    public static void main(String[] args) {
        File f = new File("/home/xixisun/tmp/forge.jar");
        long val = 0;
        try {
            byte[] bytes = Files.readAllBytes(f.toPath());
            Checksum checksum = new Adler32();
            checksum.update(bytes, 0, bytes.length);
            val = checksum.getValue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("target %s length = %d chksum = 0x%x", f.getName(), f.length(), val));
    }
}
