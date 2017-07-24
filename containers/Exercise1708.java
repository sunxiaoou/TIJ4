//: holding/UniqueWords.java
package containers; /* Added by Eclipse.py */

/* Exercise 17.08
 (7) Create a generic, singly linked list class called SList, which, to keep
 things simple, does not implement the List interface. Each Link object in the list should
 contain a reference to the next element in the list, but not the previous one (LinkedList, in
 contrast, is a doubly linked list, which means it maintains links in both directions). Create
 your own SListIterator which, again for simplicity, does not implement ListIterator. The
 only method in SList other than toString( ) should be iterator( ), which produces an
 SListIterator. The only way to insert and remove elements from an SList is through
 SListIterator. Write code to demonstrate SList.
 */


import java.util.*;
import java.util.Iterator;
import static net.mindview.util.Print.*;

class SList<E> {
  class Node<E> {
    E element;
    Node<E> next;
    Node(E element) { this.element = element; }
  }
  private Node<E> head = new Node<>(null);
  // private Node<E> cursor = head;


  // class SListIterator<E> implements Iterator<E> {
  class SListIterator<E> {
    private Node<E> cursor;
    SListIterator(Node<E> node) { cursor = node; }
    public boolean hasNext() { return cursor.next != null; }
    public E next() {
      E e = (E) cursor.next.element;
      if (hasNext())
        cursor = cursor.next;
      return e;
    }
    public void add(E e) {
      Node<E> tmp = new Node<>(e);
      tmp.next = cursor.next;
      cursor.next = tmp;
      cursor = cursor.next;
    }
    public void remove() {
      if (cursor.next != null) {
        Node<E> tmp = cursor.next;
        cursor.next = tmp.next;
      }
    }
  }

  // public Iterator<E> iterator() { return new SListIterator<>(); }
  public SListIterator<E> iterator() { return new SListIterator<>(head); }
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    if (head.next != null) {
      for (Node<E> node = head.next; node != null; node = node.next)
        sb.append(node.element + ", ");
      sb.delete(sb.length() - 2, sb.length());
    }
    sb.append("}");
    return sb.toString();
  }
}

public class Exercise1708 {
  public static void main(String[] args) {
    SList<String> sList = new SList<>();
    SList<String>.SListIterator<String> it = sList.iterator();
    for (String s : Arrays.asList("one", "two", "three", "four", "five", "six", "seven"))
      it.add(s);
    print(sList);

    it = sList.iterator();
    it.add("zero");
    print(sList);
    it.remove();
    it.remove();
    print(sList);
  }
}