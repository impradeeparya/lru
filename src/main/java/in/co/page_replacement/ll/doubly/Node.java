package in.co.page_replacement.ll.doubly;

public class Node {

  private String data;
  private Node next;
  private Node previous;

  public String getData() {
    return data;
  }

  public Node setData(String data) {
    this.data = data;
    return this;
  }

  public Node getNext() {
    return next;
  }

  public Node setNext(Node next) {
    this.next = next;
    return this;
  }

  public Node getPrevious() {
    return previous;
  }

  public void setPrevious(Node previous) {
    this.previous = previous;
  }

  @Override
  public String toString() {
    return "Node{" + "data='" + data + '\'' + '}';
  }
}
