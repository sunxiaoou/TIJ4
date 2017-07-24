//: exceptions/FullConstructors.java
package exceptions; /* Added by Eclipse.py */

/* Exercise 12.28
 Modify Exercise 4 so that the custom exception class inherits from
 RuntimeException, and show that the compiler allows you to leave out the try block.
 */

class ExceptionM2 extends RuntimeException {
    private String msg;
    ExceptionM2(String msg) { this.msg = msg; }
    public String msg() { return msg; }
}

public class Exercise1228 {
    public static void main(String[] args) {
    /*
        try {
            throw new ExceptionM("A exception occurs");
        } catch (ExceptionM e) {
            System.out.println(e.msg());
            e.printStackTrace(System.out);
        }
    */
        throw new ExceptionM2("A exception occurs");
    }
}