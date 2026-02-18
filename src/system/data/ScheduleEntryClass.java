package system.data;

import system.base.Station;
import system.base.Time;

import java.io.Serial;

/**
 * @param station Station corresponding to this entry of a schedule.
 * @param time    Time corresponding to this entry of a schedule.
 */
public record ScheduleEntryClass(Station station, Time time) implements ScheduleEntry {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Constructor of a pair Time-Station, aka schedule entry.
     * @param station Station associated.
     * @param time    Time associated.
     */
    public ScheduleEntryClass {
    }

    public int compareTo(ScheduleEntry o) {
        int res = time.compareTo(o.time());
        if (res == 0) res = station.getName().compareTo(o.station().getName());
        return res;
    }
}
