//: holding/StackTest.java
package holding; /* Added by Eclipse.py */

import net.mindview.util.Stack;

/* Exercise 11.15
 Stacks are often used to evaluate expressions in programming languages.
 Using net.mindview.util.Stack, evaluate the following expression, where’+’ means "push
 the following letter onto the stack," and’-’ means "pop the top of the stack and print it":
 "+U+n+c---+e+r+t---+a-+i-+n+t+y---+ -+r+u--+l+e+s---"
*/


public class Exercise1115 {

  public void evaluate(String s) {
    Stack<String> stack = new Stack<>();
    char array[] = s.toCharArray();
    for (int i = 0; i < array.length; i ++) {
      switch (array[i]) {
        case '+':
          stack.push(String.valueOf(array[++i]));
          break;
        case '-':
          System.out.print(stack.pop());
          break;
        default:
          System.out.print("(" + array[i] + ")");
          break;
      }
    }
  }

  public static void main(String[] args) {
    String s = "+U+n+c---+e+r+t---+a-+i-+n+t+y---+ -+r+u--+l+e+s---";
    Exercise1115 expr = new Exercise1115();
    expr.evaluate(s);
  }
}