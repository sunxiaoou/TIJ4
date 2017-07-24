//: concurrency/MapComparisons.java
package concurrency; /* Added by Eclipse.py */
// {Args: 1 10 10} (Fast verification check during build)
// Rough comparison of thread-safe Map performance.


/* Exercise 21.40
 (6) Following the example of ReaderWriterList.java, create a
 ReaderWriterMap using a HashMap. Investigate its performance by modifying
 MapComparisons.java. How does it compare to a synchronized HashMap and a
 ConcurrentHashMap?
 */


import net.mindview.util.CountingGenerator;
import net.mindview.util.MapData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteMap<K, V> extends HashMap<K, V> {
  private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

  ReadWriteMap(Map<? extends K, ? extends V> m) {
    super(m);
  }

  @Override
  public V get(Object key) {
    Lock rLock = lock.readLock();
    rLock.lock();
    try {
      return super.get(key);
    } finally {
      rLock.unlock();
    }
  }

  @Override
  public V put(K key, V value) {
    Lock wLock = lock.writeLock();
    wLock.lock();
    try {
      return super.put(key, value);
    } finally {
      wLock.unlock();
    }
  }
}

class ReadWriteMapTest extends MapTest {
  Map<Integer,Integer> containerInitializer() {
    return new ReadWriteMap<>(
            MapData.map(new CountingGenerator.Integer(), new CountingGenerator.Integer(), containerSize));
  }
  ReadWriteMapTest(int nReaders, int nWriters) {
    super("ReadWriteMap", nReaders, nWriters);
  }
}


public class Exercise2140 {
  public static void main(String[] args) {
    Tester.initMain(args);
    new SynchronizedHashMapTest(10, 0);
    new SynchronizedHashMapTest(9, 1);
    new SynchronizedHashMapTest(5, 5);
    new ConcurrentHashMapTest(10, 0);
    new ConcurrentHashMapTest(9, 1);
    new ConcurrentHashMapTest(5, 5);
    new ReadWriteMapTest(10, 0);
    new ReadWriteMapTest(9, 1);
    new ReadWriteMapTest(5, 5);
    Tester.exec.shutdown();
  }
} /* Output: (Sample)
Type                             Read time     Write time
Synched HashMap 10r 0w        306052025049              0
Synched HashMap 9r 1w         428319156207    47697347568
readTime + writeTime =        476016503775
Synched HashMap 5r 5w         243956877760   244012003202
readTime + writeTime =        487968880962
ConcurrentHashMap 10r 0w       23352654318              0
ConcurrentHashMap 9r 1w        18833089400     1541853224
readTime + writeTime =         20374942624
ConcurrentHashMap 5r 5w        12037625732    11850489099
readTime + writeTime =         23888114831
*///:~