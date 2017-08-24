// Basic task cooperation.
package tmp;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


class Stage {
  private boolean [] doors = new boolean[3];  // door1, door2 and door3
  private boolean ready = false;              // done by host, ready for player
  private int playerDoor = -1;                // door# selected by player
  private int hostDoor = -1;                  // door# opened by host (a sheep)
  int totals  = 0;                            // total times of test
  int cars = 0;                               // got car times

  synchronized void initByHost(int i) {       // Step 1
    playerDoor = hostDoor = -1;
    Arrays.fill(doors, false);            // false represents sheep
    doors[i] = true;                          // true represents car
    ready = true;
    notifyAll();
  }

  synchronized void selectByPlayer(int i) {   // Step 2
    playerDoor = i;
    ready = false;
    notifyAll();
  }

  synchronized void openByHost() {            // Step 3, host opens a door (a sheep)
    for (int i = 0; i < doors.length; i ++) {
      if (i != playerDoor && ! doors[i]) {
        hostDoor = i;
        break;
      }
    }
    ready = true;
    notifyAll();
  }

  synchronized void openByPlayer(boolean change) {  // Step 4
    if (change) {                                   // player want to change or not
      for (int i = 0; i < doors.length; i ++) {
        if (i != playerDoor && i != hostDoor) {
          playerDoor = i;
          break;
        }
      }
    }
    totals ++;
    if (doors[playerDoor]) {
      cars ++;
      // print("CAR!");
    }
    // else
    //  print("sheep");
    ready = false;
    notifyAll();
  }

  synchronized void waitForHost() throws InterruptedException {
    while(! ready)
      wait();
  }

  synchronized void waitForPlayer() throws InterruptedException {
    while(ready)
      wait();
  }
}

class Host implements Runnable {
  private Stage stage;
  private int num;
  private Random rand = new Random(42);
  Host(Stage s, int n) { stage = s; num = n; }
  public void run() {
    try {
      for (int i = 0; i < num && ! Thread.interrupted(); i ++) {
        // System.out.print(i + " ");
        stage.initByHost(rand.nextInt(3));
        stage.waitForPlayer();
        stage.openByHost();
        stage.waitForPlayer();
      }
    } catch(InterruptedException e) {
      System.out.println("Exiting via interrupt");
    }
    // System.out.println("Ending Host task");
  }
}

class Player implements Runnable {
  private Stage stage;
  private int num;
  private boolean change;
  private Random rand = new Random(47);
  Player(Stage s, int n, boolean c) { stage = s; num = n; change = c; }
  public void run() {
    try {
      for (int i = 0; i < num && ! Thread.interrupted(); i ++) {
        stage.waitForHost();
        stage.selectByPlayer(rand.nextInt(3));
        stage.waitForHost();
        stage.openByPlayer(change);
      }
    } catch(InterruptedException e) {
      System.out.println("Exiting via interrupt");
    }
    // System.out.println("Ending Player task");
  }
}

public class SheepOrCar {
  private static int counter = 0;

  private SheepOrCar(int num, boolean change) throws InterruptedException {
    final int id =  ++ counter;
    System.out.printf("Test%02d: %d times %s change\n", id, num, change ? "with" : "without");
    Stage stage = new Stage();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new Host(stage, num));
    exec.execute(new Player(stage, num, change));
    TimeUnit.SECONDS.sleep(5); // Run for a while...
    exec.shutdownNow(); // Interrupt all tasks
    System.out.printf("Result: Total(%d)Cars(%d)Rate(%%%d)\n\n",
            stage.totals, stage.cars, stage.cars * 100 / stage.totals);
  }

  public static void main(String[] args) throws Exception {
    new SheepOrCar(1000, false);
    new SheepOrCar(1000, true);
  }
}