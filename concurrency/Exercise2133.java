//: concurrency/GreenhouseScheduler.java
package concurrency; /* Added by Eclipse.py */
// Rewriting innerclasses/GreenhouseController.java
// to use a ScheduledThreadPoolExecutor.
// {Args: 5000}


/* Exercise 21.33
 (7) Modify GreenhouseScheduler.java so that it uses a DelayQueue
 instead of a ScheduledExecutor.
*/

import java.util.*;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class Exercise2133 {
  private volatile boolean light = false;
  private volatile boolean water = false;
  private String thermostat = "Day";
  synchronized String getThermostat() { return thermostat; }
  private synchronized void setThermostat(String value) { thermostat = value; }

  abstract class Task implements Delayed {
    private final int delta;
    private long trigger;

    Task(int delayInMilliseconds) {
      delta = delayInMilliseconds;
      trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
    }
    @Override
    public int compareTo(Delayed o) {
      Task that = (Task)o;
      if (trigger < that.trigger) return -1;
      if (trigger > that.trigger) return 1;
      return 0;
    }
    @Override
    public long getDelay(TimeUnit unit) {
      return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }
    public void reset() {
      trigger = System.nanoTime() + NANOSECONDS.convert(delta, MILLISECONDS);
    }
    abstract public void run();
  }

  class LightOn extends Task {
    LightOn(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() {
      // Put hardware control code here to
      // physically turn on the light.
      System.out.println("Turning on lights");
      light = true;
    }
  }
  class LightOff extends Task {
    LightOff(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() {
      // Put hardware control code here to
      // physically turn off the light.
      System.out.println("Turning off lights");
      light = false;
    }
  }
  class WaterOn extends Task {
    WaterOn(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() {
      // Put hardware control code here.
      System.out.println("Turning greenhouse water on");
      water = true;
    }
  }
  class WaterOff extends Task {
    WaterOff(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() {
      // Put hardware control code here.
      System.out.println("Turning greenhouse water off");
      water = false;
    }
  }

  class ThermostatNight extends Task {
    ThermostatNight(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() {
      // Put hardware control code here.
      System.out.println("Thermostat to night setting");
      setThermostat("Night");
    }
  }
  class ThermostatDay extends Task {
    ThermostatDay(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() {
      // Put hardware control code here.
      System.out.println("Thermostat to day setting");
      setThermostat("Day");
    }
  }
  class Bell extends Task {
    Bell(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() { System.out.println("Bing!"); }
  }
  class Terminate extends Task {
    Terminate(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() {
      System.out.println("Terminating");
      for(DataPoint d : data)
        System.out.println(d);
    }
  }
  // New feature: data collection
  static class DataPoint {
    final Calendar time;
    final float temperature;
    final float humidity;
    DataPoint(Calendar d, float temp, float hum) {
      time = d;
      temperature = temp;
      humidity = hum;
    }
    public String toString() {
      return time.getTime() +
        String.format(
          " temperature: %1$.1f humidity: %2$.2f",
          temperature, humidity);
    }
  }

  private Calendar lastTime = Calendar.getInstance();
  { // Adjust date to the half hour
    lastTime.set(Calendar.MINUTE, 30);
    lastTime.set(Calendar.SECOND, 0);
  }
  private float lastTemp = 65.0f;
  private int tempDirection = +1;
  private float lastHumidity = 50.0f;
  private int humidityDirection = +1;
  private Random rand = new Random(47);
  private List<DataPoint> data = Collections.synchronizedList(new ArrayList<DataPoint>());
  class CollectData extends Task {
    CollectData(int delayInMilliseconds) { super(delayInMilliseconds); }
    public void run() {
      System.out.println("Collecting data");
      synchronized(Exercise2133.this) {
        // Pretend the interval is longer than it is:
        lastTime.set(Calendar.MINUTE,
          lastTime.get(Calendar.MINUTE) + 30);
        // One in 5 chances of reversing the direction:
        if(rand.nextInt(5) == 4)
          tempDirection = -tempDirection;
        // Store previous value:
        lastTemp = lastTemp +
          tempDirection * (1.0f + rand.nextFloat());
        if(rand.nextInt(5) == 4)
          humidityDirection = -humidityDirection;
        lastHumidity = lastHumidity +
          humidityDirection * rand.nextFloat();
        // Calendar must be cloned, otherwise all
        // DataPoints hold references to the same lastTime.
        // For a basic object like Calendar, clone() is OK.
        data.add(new DataPoint((Calendar)lastTime.clone(),
          lastTemp, lastHumidity));
      }
    }
  }

  private Exercise2133() throws InterruptedException {
    List<Task> tasks = new ArrayList<>(Arrays.asList(
            new Bell(1000),
            new ThermostatNight(2000),
            new LightOn(200),
            new LightOff(400),
            new WaterOn(600),
            new WaterOff(800),
            new ThermostatDay(1400)));

    DelayQueue<Task> queue = new DelayQueue<>();
    for (Task task : tasks) {
      task.run();
      queue.add(task);
    }
    queue.add(new CollectData(500));
    queue.add(new Terminate(5000));

    while (true) {
      Task task = queue.take();
      task.run();
      if (task instanceof Terminate)
        break;
      task.reset();
      queue.add(task);
    }
  }

  public static void main(String[] args) throws Exception {
    new Exercise2133();
  }
}