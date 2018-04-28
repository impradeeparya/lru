package in.co.page_replacement.thread_safe;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadWriteLock {

  private final Logger LOGGER = LogManager.getLogger(ReadWriteLock.class);

  private int readers = 0;
  private int writers = 0;
  private int writeRequests = 0;

  public synchronized void lockRead() throws InterruptedException {
    while (writers > 0 || writeRequests > 0) {
      wait();
    }
    readers++;
  }

  public synchronized void unlockRead() {
    readers--;
    notifyAll();
  }

  public synchronized void lockWrite() {

    try {
      writeRequests++;

      while (readers > 0 || writers > 0) {
        wait();
      }
      writeRequests--;
      writers++;
    } catch (InterruptedException e) {
      LOGGER.error("lockWrite {} ", e);
    }
  }

  public synchronized void unlockWrite() {
    writers--;
    notifyAll();
  }
}
