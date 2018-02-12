package synchronization;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {

    private String myLockedValue = "initialValue";
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public String getMyLockedValue() {
        System.out.printf("\n %s Start reading value", Thread.currentThread().getId());
        lock.readLock().lock();
        String value=myLockedValue;
        lock.readLock().unlock();
        System.out.printf("\n %s Finish reading value", Thread.currentThread().getId());
        return value;
    }

    public void setMyLockedValue(String myLockedValue) {
        System.out.printf("\n %s Start writing value", Thread.currentThread().getId());
        lock.writeLock().lock();
        try {
            // wait to check readlock
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.myLockedValue = myLockedValue;
        lock.writeLock().unlock();
        System.out.printf("\n %s Finish writing value", Thread.currentThread().getId());
    }
}
