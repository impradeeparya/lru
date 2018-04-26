package in.co.page_replacement.ll.doubly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DoublyLinkedCache {

  private final Logger LOGGER = LogManager.getLogger(DoublyLinkedCache.class);

  private Node head;
  private final int MAX_SIZE = 3;

  public DoublyLinkedCache() {}

  public Node addToHead(Node node) {

    if (this.head == null) {
      this.head = node;
    } else {
      if (isCacheFull()) {
        removeLru();
      }
      this.head.setPrevious(node);
      node.setNext(this.head);
      node.setPrevious(null);
      this.head = node;
    }
    return node;
  }

  private void removeLru() {
    Node node = this.head;
    while (node.getNext() != null) {
      node = node.getNext();
    }
    Node prevNode = node.getPrevious();
    prevNode.setNext(null);
    LOGGER.info("removing {} from cache", node.getData());
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
    previous.setNext(node.getNext());
    node.getNext().setPrevious(previous);
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
          addToHead(newNode);
          node = newNode;
        }
      }
    } else {
      String memoryValue = fetchFromMemory(value);
      if (memoryValue != null) {
        Node newNode = new Node().setData(memoryValue);
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
    Node node = this.head;
    while (node != null) {
      System.out.print(node.getData() + " ");
      node = node.getNext();
    }
    System.out.println();
  }
}
