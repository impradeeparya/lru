package in.co.page_replacement;

import java.util.stream.IntStream;

import in.co.page_replacement.ll.doubly.DoublyLinkedCache;
import in.co.page_replacement.ll.singly.SinglyLinkedCache;

public class CacheTester {

  static {
    System.setProperty("log4j.configurationFile", "log4j2.json");
  }


  public static void main(String[] args) {
    SinglyLinkedCache singlyLinkedCache = new SinglyLinkedCache();

    IntStream.range(0, 10).forEach(value -> {
      singlyLinkedCache.find(String.valueOf(value));
    });

    singlyLinkedCache.print();

    System.out.println("################################################");

    DoublyLinkedCache doublyLinkedCache = new DoublyLinkedCache();
    IntStream.range(0, 10).forEach(value -> {
      doublyLinkedCache.find(String.valueOf(value));
    });

    doublyLinkedCache.print();
  }
}
