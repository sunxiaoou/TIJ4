package tmp;


public class Retry {
    private static void doSomething(int i) {
        if(i < 1)
            throw new RuntimeException();
        System.out.println("do something");
    }

    public static void main(String[] args) throws Exception {
        int maxTries = 3;
        int count = 0;
        while (true) {
            try {
                doSomething(count);
                break;
            } catch (RuntimeException e) {
                if (++ count == maxTries)
                    throw e;
                System.out.println(e.getClass().getName());
                Thread.sleep(1000);
            }
        }
    }
}
