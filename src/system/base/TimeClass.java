package system.base;

import java.io.Serial;

public class TimeClass implements Time {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Hours and minutes of the time.
     */
    private final int hour, min;

    /**
     * Time object constructor.
     * @param hour String of hours.
     * @param min String of minutes.
     */
    public TimeClass(String hour, String min) {
        this.hour = Integer.parseInt(hour);
        this.min = Integer.parseInt(min);
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int compareTo(Time o) {
        int res = hour - o.getHour();
        if(res == 0) res = min - o.getMin();
        return res;
    }
}
