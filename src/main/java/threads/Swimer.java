package threads;

public class Swimer extends Thread {
    public void run() {
        while (true) {
            System.out.printf(getName() + swim(Math.random()));


            // When is interrupted, write a message and ends
            if (isInterrupted()) {
                System.out.printf(getName() + " is dead");
                return;
            }
        }
    }

    private String swim(double random) {
        if(random < 0.4) {
            return " swims to the left";
        } else if (random < 0.7) {
            return " swims to the right";
        }
        return " swims straight forward";
    }
}
