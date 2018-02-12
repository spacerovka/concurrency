package collections;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class EventWithDelay implements Delayed {
    private final LocalDateTime startDate;

    public EventWithDelay(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public int compareTo(Delayed o) {
        long result = this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay
                (TimeUnit.NANOSECONDS);
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        }
        return 0;

    }

    @Override
    public long getDelay(TimeUnit unit) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, startDate);
        return duration.toNanos();
    }
}
