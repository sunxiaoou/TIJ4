//: innerclasses/controller/Event.java
// The common methods for any control event.
package innerclasses.controller;

public abstract class Event {
  private long eventTime;
  protected final long delayTime;
  public Event(long delayTime) {
    // System.out.println(delayTime);
    this.delayTime = delayTime;
    start();
  }
  public void start() { // Allows restarting
    // eventTime = System.nanoTime() + delayTime;
    eventTime = System.nanoTime() + delayTime * 1000000;  // use milliseconds to make sense
  }
  public boolean ready() {
    return System.nanoTime() >= eventTime;
  }
  public abstract void action();
} ///:~
