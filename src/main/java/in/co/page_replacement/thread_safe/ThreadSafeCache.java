package in.co.page_replacement.thread_safe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import in.co.page_replacement.ll.doubly.Node;

public class ThreadSafeCache {

  private final Logger LOGGER = LogManager.getLogger(ThreadSafeCache.class);

  private Node head;
  private final int MAX_SIZE = 20;
  private final ReadWriteLock readWriteLock;
  private final Lock readLock;
  private final Lock writeLock;

  public ThreadSafeCache() {
    this.readWriteLock = new ReentrantReadWriteLock(true);
    this.readLock = readWriteLock.readLock();
    this.writeLock = readWriteLock.writeLock();
  }

  public Node addToHead(Node node) {

    if (this.head == null) {
      this.head = node;
    } else {
      this.head.setPrevious(node);
      node.setNext(this.head);
      node.setPrevious(null);
      this.head = node;
    }
    return node;
  }

  private void removeLru() {
    try {
      writeLock.lock();
      if (this.head != null) {
        Node node = this.head;
        while (node.getNext() != null) {
          node = node.getNext();
        }
        Node prevNode = node.getPrevious();
        if (prevNode != null)
          prevNode.setNext(null);
        LOGGER.info("removing {} from cache", node.getData());
      }
    } finally {
      writeLock.unlock();
    }
  }

  private boolean isCacheFull() {
    Node node = this.head;
    int counter = 0;
    while (node != null) {
      counter++;
      node = node.getNext();
    }

    return counter >= MAX_SIZE;
  }

  public Node moveToHead(Node node) {
    LOGGER.info("updating cache for value {}", node.getData());
    Node previous = node.getPrevious();
    if (previous != null) {
      previous.setNext(node.getNext());
    }
    Node next = node.getNext();
    if (next != null) {
      next.setPrevious(previous);
    }
    addToHead(node);
    return node;
  }

  public String find(String value) {

    Node node = this.head;

    if (node != null) {

      while (node != null && !value.equals(node.getData())) {
        node = node.getNext();
      }
      if (node != null) {
        LOGGER.info("fetching {} from cache", value);
        moveToHead(node);
      } else {
        String memoryValue = fetchFromMemory(value);
        if (memoryValue != null) {
          Node newNode = new Node().setData(memoryValue);
          if (isCacheFull()) {
            removeLru();
          }
          addToHead(newNode);
          node = newNode;
        }
      }
    } else {
      String memoryValue = fetchFromMemory(value);
      if (memoryValue != null) {
        Node newNode = new Node().setData(memoryValue);
        if (isCacheFull()) {
          removeLru();
        }
        addToHead(newNode);
        node = newNode;
      }
    }
    return node == null ? null : node.getData();

  }

  private String fetchFromMemory(String value) {
    LOGGER.info("fetching {} from memory", value);
    return value;
  }

  public void print() {
    LOGGER.info("current cache");
    Node node = this.head;
    while (node != null) {
      System.out.print(node.getData() + " ");
      node = node.getNext();
    }
    System.out.println();
  }
}
