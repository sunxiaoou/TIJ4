package tmp;

public class LocateClass {
    public static void main(String[] args) {
        LocateClass lc = new LocateClass();
        Class cls = lc.getClass();
        String name = cls.getName();
        System.out.println("name: " + name);
        System.out.println("location: " + cls.getResource('/' + name.replace('.', '/') + ".class"));
    }
}