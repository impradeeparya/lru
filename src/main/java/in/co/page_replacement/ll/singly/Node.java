package in.co.page_replacement.ll.singly;

public class Node {

  private String data;
  private Node next;

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

  @Override
  public String toString() {
    return "Node{" + "data=" + data + ", next=" + next + '}';
  }
}
