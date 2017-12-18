package tmp;

/* Give you eight 8, plus or minus multiplication and division of any combination, how to get a thousand？
   for example: 8 * 8 * 8 + 8 * 8 * 8 - 8 - 8 - 8 = 1000
 */

public class Eight8 {
    static private int expr(int a, int b, char op) {
        int c = -1;
        switch (op) {
            case '+':
                c = a + b;
                break;
            case '-':
                c = a - b;
                break;
            case '*':
                c = a * b;
                break;
            case '/':
                c = a / b;
                break;
        }
        return c;
    }

    public static void main(String[] args) throws Exception {
        char [] ops = {'+', '-', '*', '/'};
        for (char a : ops)
            for (char b : ops) {
                System.out.print("8 " + a + " 8 " + b + " 8 = ");
                System.out.println(expr(expr(8, 8, a), 8, b));
            }
    }
}
