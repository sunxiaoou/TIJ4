//: holding/UniqueWordsAlphabetic.java
package holding; /* Added by Eclipse.py */


/* Exercise 11.27
 Write a class called Command that contains a String and has a
 method operation( ) that displays the String. Write a second class with a method that fills
 a Queue with Command objects and returns it. Pass the filled Queue to a method in a
 third class that consumes the objects in the Queue and calls their operation( ) methods.
*/


import java.util.*;

class Command {
  private String command;
  Command(String command) { this.command = command; }
  public void operation() { System.out.println(command);}
}

class Producer {
  private Queue<Command> queue;
  Producer() { queue = new LinkedList<>();; }
  Queue<Command> produce(String[] commands) {
    for (String command : commands)
      queue.offer(new Command(command));
    return queue;
  }
}

class Consumer {
  void consume(Queue<Command> queue) {
    Command C;
    while ((C = queue.poll()) != null)
      C.operation();
  }
}

public class Exercise1127 {

  public static void main(String[] args) {
    String commands[] = {"offer", "peek", "poll", "remove"};
    new Consumer().consume(new Producer().produce(commands));
  }
}