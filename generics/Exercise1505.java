//: generics/LinkedStack.java
package generics; /* Added by Eclipse.py */
// A stack implemented with an internal linked structure.

/* Exercise 15.05
 (2) Remove the type parameter on the Node class and modify the rest of the
 code in LinkedStack.java to show that an inner class has access to the generic type
 parameters of its outer class.
 */

public class Exercise1505<T> {
  private static class Node<T> {
    T item;
    Node<T> next;
    Node() { item = null; next = null; }
    Node(T item, Node<T> next) {
      this.item = item;
      this.next = next;
    }
    boolean end() { return item == null && next == null; }
  }
  private Node<T> top = new Node<T>(); // End sentinel
  public void push(T item) {
    top = new Node<T>(item, top);
  }	
  public T pop() {
    T result = top.item;
    if(!top.end())
      top = top.next;
    return result;
  }
  public static void main(String[] args) {
    Exercise1505<String> lss = new Exercise1505<String>();
    for(String s : "Phasers on stun!".split(" "))
      lss.push(s);
    String s;
    while((s = lss.pop()) != null)
      System.out.println(s);
  }
}
