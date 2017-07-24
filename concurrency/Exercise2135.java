//: concurrency/BankServerSimulation.java
package concurrency; /* Added by Eclipse.py */


/* Exercise 21.35
 (8) Modify BankTellerSimulation.java so that it represents Web clients
 making requests of a fixed number of servers. The goal is to determine the load that the
 group of servers can handle.
 */


import java.util.concurrent.*;
import java.util.*;

import static net.mindview.util.Print.*;

public class Exercise2135 {
  private static Random rand = new Random(47);
  private ArrayBlockingQueue<Request> requests;
  private static List<Server> servers = new ArrayList<>();

  // Read-only objects don't require synchronization:
  class Request {
    private final int serviceTime;
    Request(int tm) { serviceTime = tm; }
    int getServiceTime() { return serviceTime; }
  }

  // Randomly add customers to a queue:
  class RequestGenerator implements Runnable {
    private long totalCount = 0;
    public void run() {
      try {
        while(!Thread.interrupted()) {
          // TimeUnit.MILLISECONDS.sleep(rand.nextInt(10));
          requests.put(new Request(rand.nextInt(1000)));
          synchronized(this) {
            totalCount ++;
          }
        }
      } catch(InterruptedException e) {
        System.out.println("RequestGenerator interrupted");
      }
      System.out.println("RequestGenerator terminating");
    }
    synchronized long getTotalCount() { return totalCount; }
  }

  class Server implements Runnable {
    private int id;
    private long number = 0;
    Server(int id) {
      this.id = id;
      servers.add(this);
    }
    public void run() {
      try {
        while(! Thread.interrupted()) {
          Request request = requests.take();
          TimeUnit.MILLISECONDS.sleep(request.getServiceTime());
          synchronized(this) {
            number ++;
          }
        }
      } catch(InterruptedException e) {
        System.out.println(this + "interrupted");
      }
      System.out.println(this + "terminating");
    }
    public String toString() { return "Server " + id + " "; }
    synchronized long getValue() { return number; }
  }

  private static int sumServers() {
    int sum = 0;
    for(Server server : servers)
      sum += server.getValue();
    return sum;
  }

  private Exercise2135(int nServers, int elapse) throws InterruptedException {
    requests = new ArrayBlockingQueue<>(nServers * 2);
    ExecutorService exec = Executors.newCachedThreadPool();
    RequestGenerator generator = new RequestGenerator();
    exec.execute(generator);
    for(int i = 0; i < nServers; i ++)
      exec.execute(new Server(i));
    TimeUnit.SECONDS.sleep(elapse);
    exec.shutdownNow();
    if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
      print("Some tasks were not terminated!");
    long total = generator.getTotalCount();
    long left = requests.size();
    long done = total - left;
    assert done == sumServers();
    printf("Total(%d)Left(%d)Done(%d)\n", total, left, done);
    printf("Throughput: %d\n", done / elapse);
  }

  public static void main(String[] args) throws Exception {
    new Exercise2135(20, 5);
  }
}