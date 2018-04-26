package in.co.page_replacement.ll.singly;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SinglyLinkedCache {

  private final Logger LOGGER = LogManager.getLogger(SinglyLinkedCache.class);

  private Node head;
  private final int MAX_SIZE = 7;

  public SinglyLinkedCache() {}

  public Node addToHead(Node node) {

    if (this.head == null) {
      this.head = node;
    } else {
      if (isCacheFull()) {
        removeLru();
      }
      node.setNext(this.head);
      this.head = node;
    }
    return node;
  }

  private void removeLru() {
    Node node = this.head;
    Node prevNode = node;
    while (node.getNext() != null) {
      prevNode = node;
      node = node.getNext();
    }

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

    return counter < MAX_SIZE ? false : true;
  }

  public Node moveToHead(Node prevNode, Node node) {
    LOGGER.info("updating cache for value {}", node.getData());
    prevNode.setNext(node.getNext());
    addToHead(node);
    return node;
  }

  public String find(String value) {

    Node node = this.head;

    if (node != null) {

      Node prevNode = null;
      while (node != null && !value.equals(node.getData())) {
        prevNode = node;
        node = node.getNext();
      }
      if (node != null) {
        LOGGER.info("fetching {} from cache", value);
        moveToHead(prevNode, node);
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
