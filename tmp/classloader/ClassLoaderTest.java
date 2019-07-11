package tmp.classloader;

/**
    https://www.journaldev.com/349/java-classloader
    Java ClassLoader are hierarchical and whenever a request is raised to load a class,
    it delegates it to its parent and in this way uniqueness is maintained in the runtime environment.
    If the parent class loader doesn’t find the class then the class loader itself tries to load the class.
 */

public class ClassLoaderTest {

    public static void main(String[] args) {

        // 1.Bootstrap Class Loader – null
        // It loads JDK internal classes, typically loads rt.jar
        // and other core classes for example java.lang.* package classes
        System.out.println("class loader for HashMap: "
                + java.util.HashMap.class.getClassLoader());

        // 2. Extensions Class Loader – sun.misc.Launcher$ExtClassLoader
        // It loads classes from the JDK extensions directory,
        // usually $JAVA_HOME/lib/ext directory.
        System.out.println("class loader for DNSNameService: "
                + sun.net.spi.nameservice.dns.DNSNameService.class.getClassLoader());

        // 3. System Class Loader – sun.misc.Launcher$AppClassLoader
        // It loads classes from the current classpath
        // that can be set while invoking a program using -cp or -classpath command line options.
        System.out.println("class loader for this class: "
                + ClassLoaderTest.class.getClassLoader());

        // Note that Blob class is included in the MySql JDBC Connector jar (mysql-connector-java-5.0.7-bin.jar)
        // that I have included in the build path of the project before executing it
        // and its also getting loaded by System Class Loader.
        // System.out.println(com.mysql.jdbc.Blob.class.getClassLoader());
    }
}