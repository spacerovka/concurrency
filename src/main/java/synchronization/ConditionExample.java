package synchronization;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    List<String> myBag = new LinkedList<String>();
    int CAPACITY = 5;

    ReentrantLock lock = new ReentrantLock();
    Condition bagEmptyCondition = lock.newCondition();
    Condition bagFullCondition = lock.newCondition();

    public void pushToBag(String item) throws InterruptedException {
        try {
            lock.lock();
            while (myBag.size() == CAPACITY) {
                bagFullCondition.await();
            }
            myBag.add(item);
            bagEmptyCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public String getFromBag() throws InterruptedException {
        try {
            lock.lock();
            while (myBag.size() == 0) {
                bagEmptyCondition.await();
            }
            return myBag.remove(myBag.size() - 1);
        } finally {
            bagFullCondition.signalAll();
            lock.unlock();
        }
    }
}
