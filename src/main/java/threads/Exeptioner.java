package threads;

public class Exeptioner implements Runnable {
    
    public void run() {
        throw new IllegalArgumentException();
    }
}
