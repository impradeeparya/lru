package in.co.page_replacement;

import java.util.Collections;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import in.co.page_replacement.ll.doubly.DoublyLinkedCache;
import in.co.page_replacement.ll.singly.SinglyLinkedCache;
import in.co.page_replacement.thread_safe.LockedCache;
import in.co.page_replacement.thread_safe.ThreadSafeCache;

public class CacheTester {

  private static final Logger LOGGER = LogManager.getLogger(CacheTester.class);

  static {
    System.setProperty("log4j.configurationFactory",
        "org.apache.logging.log4j.core.config.json.JsonConfigurationFactory");
    System.setProperty("log4j.configurationFile", "log4j2.json");
  }


  public static void main(String[] args) {

    // singlyLinkedCache();
    // doublyLinkedCache();

    // threadedCache();

    // threadSafeCache_1();
    threadSafeCache_2();
  }

  private static void threadSafeCache_2() {
    LOGGER.info("threadSafeCache_2");
    LockedCache lockedCache = new LockedCache();

    Thread t1 = new Thread(() -> {
      IntStream.range(0, 10).forEach(value -> {
        lockedCache.find(String.valueOf(value));
      });
    });
    Thread t2 = new Thread(() -> {
      IntStream.range(0, 10).boxed().sorted(Collections.reverseOrder()).forEach(value -> {
        lockedCache.find(String.valueOf(value));
      });
    });
    t1.start();
    t2.start();
    lockedCache.print();
    LOGGER.info("################################################");
  }

  private static void threadSafeCache_1() {
    LOGGER.info("threadSafeCache_1");
    ThreadSafeCache threadSafeCache = new ThreadSafeCache();

    Thread t1 = new Thread(() -> {
      IntStream.range(0, 10).forEach(value -> {
        threadSafeCache.find(String.valueOf(value));
      });
    });
    Thread t2 = new Thread(() -> {
      IntStream.range(0, 10).boxed().sorted(Collections.reverseOrder()).forEach(value -> {
        threadSafeCache.find(String.valueOf(value));
      });
    });
    t1.start();
    t2.start();
    threadSafeCache.print();
    LOGGER.info("################################################");
  }

  private static void threadedCache() {
    LOGGER.info("threadedCache");
    DoublyLinkedCache doublyLinkedCache = new DoublyLinkedCache();

    Thread t1 = new Thread(() -> {
      IntStream.range(0, 10).forEach(value -> {
        doublyLinkedCache.find(String.valueOf(value));
      });
    });
    Thread t2 = new Thread(() -> {
      IntStream.range(0, 10).boxed().sorted(Collections.reverseOrder()).forEach(value -> {
        doublyLinkedCache.find(String.valueOf(value));
      });
    });
    t1.start();
    t2.start();
    doublyLinkedCache.print();
    LOGGER.info("################################################");
  }

  private static void doublyLinkedCache() {
    LOGGER.info("doublyLinkedCache");
    DoublyLinkedCache doublyLinkedCache = new DoublyLinkedCache();
    IntStream.range(0, 10).forEach(value -> {
      doublyLinkedCache.find(String.valueOf(value));
    });
    doublyLinkedCache.print();
    LOGGER.info("################################################");
  }

  private static void singlyLinkedCache() {
    LOGGER.info("singlyLinkedCache");
    SinglyLinkedCache singlyLinkedCache = new SinglyLinkedCache();

    IntStream.range(0, 10).forEach(value -> {
      singlyLinkedCache.find(String.valueOf(value));
    });
    singlyLinkedCache.print();
    LOGGER.info("################################################");
  }
}
