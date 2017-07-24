//: concurrency/restaurant2/E36_RestaurantWithQueues2.java
// {Args: 5}
package concurrency;

/******************** Exercise 36 ************************
 * Modify RestaurantWithQueues.java so there’s one
 * OrderTicket object per table. Change order to
 * orderTicket, and add a Table class, with multiple
 * Customers per table.
 *********************************************************/

import enumerated.menu.*;
import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;
// This is consisted of many orders, and there's one ticket per table:

public class Exercise2136 {
    private List<WaitPerson> waitPersons = new ArrayList<>();
    private List<Chef> chefs = new ArrayList<>();
    private BlockingQueue<OrderTicket> orderTickets = new LinkedBlockingQueue<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private static Random rand = new Random(47);

    static class OrderTicket {
        private static int counter;
        private final int id = counter++;
        private final Table table;
        private final List<Order> orders = Collections.synchronizedList(new LinkedList<Order>());
        OrderTicket(Table table) { this.table = table; }
        WaitPerson getWaitPerson() { return table.getWaitPerson(); }
        void placeOrder(Customer cust, Food food) {
            Order order = new Order(cust, food);
            orders.add(order);
            order.setOrderTicket(this);
        }
        List<Order> getOrders() { return orders; }
        public String toString() {
            StringBuilder sb = new StringBuilder("Order Ticket: " + id + " for: " + table + "\n");
            synchronized(orders) {
                for(Order order : orders)
                    sb.append(order.toString()).append("\n");
            }
    // Prune away the last added 'new-line' character
            return sb.substring(0, sb.length() - 1);
        }
    }

    private static int counter;
    class Table implements Runnable {
        private final int id = counter++;
        private final WaitPerson waitPerson;
        private final List<Customer> customers;
        private final OrderTicket orderTicket = new OrderTicket(this);
        private final CyclicBarrier barrier;
        private final int nCustomers;
        Table(WaitPerson waitPerson, int nCustomers) {
            this.waitPerson = waitPerson;
            customers = Collections.synchronizedList(new LinkedList<Customer>());
            this.nCustomers = nCustomers;
            barrier = new CyclicBarrier(nCustomers + 1,
                    new Runnable() {
                        public void run() {
                            print(orderTicket.toString());
                        }
                    });
        }
        WaitPerson getWaitPerson() { return waitPerson; }
        void placeOrder(Customer cust, Food food) { orderTicket.placeOrder(cust, food); }
        public void run() {
            Customer customer;
            for(int i = 0; i < nCustomers; i++) {
                customers.add(customer = new Customer(this, barrier));
                exec.execute(customer);
            }
            try {
                barrier.await();
            } catch(InterruptedException ie) {
                print(this + " interrupted");
                return;
            } catch(BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
            waitPerson.placeOrderTicket(orderTicket);
        }
        public String toString() {
            StringBuilder sb = new StringBuilder("Table: " + id + " served by: " + waitPerson + "\n");
            synchronized(customers) {
                for(Customer customer : customers)
                    sb.append(customer.toString()).append("\n");
            }
            return sb.substring(0, sb.length() - 1);
        }
    }

    // This is part of an order ticket (given to the chef):
    static class Order {
        private static int counter;
        private final int id;
        private volatile OrderTicket orderTicket;
        private final Customer customer;
        private final Food food;
        public Order(Customer cust, Food f) {
            customer = cust;
            food = f;
            synchronized(Order.class) { id = counter++; }
        }
        void setOrderTicket(OrderTicket orderTicket) { this.orderTicket = orderTicket; }
        OrderTicket getOrderTicket() { return orderTicket; }
        public Food item() { return food; }
        Customer getCustomer() { return customer; }
        public String toString() { return "Order: " + id + " item: " + food + " for: " + customer; }
    }

    class Plate {
        private final Order order;
        private final Food food;
        Plate(Order ord, Food f) {
            order = ord;
            food = f;
        }
        public Order getOrder() { return order; }
        public Food getFood() { return food; }
        public String toString() { return food.toString(); }
    }

    static class Customer implements Runnable {
        private static int counter;
        private final int id;
        private final CyclicBarrier barrier;
        private final Table table;
        private int nPlates; // Number of plates ordered
        Customer(Table table, CyclicBarrier barrier) {
            this.table = table;
            this.barrier = barrier;
            synchronized(Customer.class) { id = counter++; }
        }
     // Only one course at a time can be received:
        private final SynchronousQueue<Plate> placeSetting = new SynchronousQueue<>();
        void deliver(Plate p) throws InterruptedException {
    // Only blocks if customer is still
    // eating the previous course:
            placeSetting.put(p);
        }
        public void run() {
    // First place an order:
            for(Course course : Course.values()) {
                Food food = course.randomSelection();
                table.placeOrder(this, food);
                nPlates ++;
            }
            try {
                barrier.await();
            } catch(InterruptedException ie) {
                print(this + " interrupted while ordering meal");
                return;
            } catch(BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
    // Now wait for each ordered plate:
            for(int i = 0; i < nPlates; i++)
                try {
    // Blocks until course has been delivered:
                    print(this + "eating " + placeSetting.take());
                } catch(InterruptedException e) {
                    print(this + "waiting for meal interrupted");
                    return;
                }
            print(this + "finished meal, leaving");
        }
        public String toString() { return "Customer " + id + " "; }
    }

    class WaitPerson implements Runnable {
        private final int id;
        final BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<>();
        public WaitPerson(int id) { this.id = id; }
        void placeOrderTicket(OrderTicket orderTicket) {
            try {
    // Shouldn't actually block because this is
    // a LinkedBlockingQueue with no size limit:
                orderTickets.put(orderTicket);
            } catch(InterruptedException e) {
                print(this + " placeOrderTicket interrupted");
            }
        }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    // Blocks until a course is ready
                    Plate plate = filledOrders.take();
                    print(this + "received " + plate + " delivering to " + plate.getOrder().getCustomer());
                    plate.getOrder().getCustomer().deliver(plate);
                }
            } catch(InterruptedException e) {
                print(this + " interrupted");
            }
            print(this + " off duty");
        }
        public String toString() { return "WaitPerson " + id + " "; }
    }

    class Chef implements Runnable {
        private final int id;
        public Chef(int id) { this.id = id; }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    // Blocks until an order ticket appears:
                    OrderTicket orderTicket = orderTickets.take();
                    List<Order> orders = orderTicket.getOrders();
                    synchronized(orders) {
                        for(Order order : orders) {
                            Food requestedItem = order.item();
                            // Time to prepare order:
                            TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                            Plate plate = new Plate(order, requestedItem);
                            order.getOrderTicket().getWaitPerson().filledOrders.put(plate);
                        }
                    }
                }
            } catch(InterruptedException e) {
                print(this + " interrupted");
            }
            print(this + " off duty");
        }
        public String toString() { return "Chef " + id + " "; }
    }

    class Restaurant implements Runnable {
        public Restaurant(int nWaitPersons, int nChefs) {
            for(int i = 0; i < nWaitPersons; i ++) {
                WaitPerson waitPerson = new WaitPerson(i);
                waitPersons.add(waitPerson);
                exec.execute(waitPerson);
            }
            for(int i = 0; i < nChefs; i ++) {
                Chef chef = new Chef(i);
                chefs.add(chef);
                exec.execute(chef);
            }
        }
        public void run() {
            try {
                while(!Thread.interrupted()) {
                    // A new group of customers arrive; assign a
                    // WaitPerson:
                    WaitPerson wp = waitPersons.get(rand.nextInt(waitPersons.size()));
                    int nCustomers = rand.nextInt(4) + 1;
                    Table t = new Table(wp, nCustomers);
                    exec.execute(t);
                    TimeUnit.MILLISECONDS.sleep(400 * nCustomers);
                }
            } catch(InterruptedException e) {
                print("Restaurant interrupted");
            }
            print("Restaurant closing");
        }
    }

    private Exercise2136() throws InterruptedException {
        Restaurant restaurant = new Restaurant(5, 2);
        exec.execute(restaurant);
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }

    public static void main(String[] args) throws Exception {
        new Exercise2136();
    }
}
