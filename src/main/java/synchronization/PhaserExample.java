package synchronization;

import java.util.concurrent.Phaser;

public class PhaserExample implements Runnable {
    private Phaser phaser;

    public PhaserExample(Phaser phaser) {
        this.phaser = phaser;
    }

    private void firstPhase() {
        System.out.println(Thread.currentThread().getId() + " has entered first phase");
        phaser.arriveAndAwaitAdvance();
    }

    private void secondPhase() {
        System.out.println(Thread.currentThread().getId() + " has entered second phase");
        phaser.arriveAndAwaitAdvance();
    }

    @Override
    public void run() {
        // Waits for the creation of all the FileSearch objects
        phaser.arriveAndAwaitAdvance();

        firstPhase();
        secondPhase();

        phaser.arriveAndDeregister();
    }
}
