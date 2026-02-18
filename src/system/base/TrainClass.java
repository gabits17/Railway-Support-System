package system.base;

import dataStructures.*;
import system.data.ScheduleEntry;

import java.io.Serial;

public class TrainClass implements Train, TrainGet {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Unique ID of the train.
     */
    private final int number;

    /**
     * Schedule entries that this train respects.
     */
    private final List<ScheduleEntry> scheduleEntries;

    public TrainClass(List<ScheduleEntry> entries , int number) {
        this.number = number;
        scheduleEntries = new DoubleList<>();
        fillEntries(entries);
    }

    /**
     * Fills the list with entries (station-time pair) of the schedule.
     * @param entries Entries of the schedule.
     */
    private void fillEntries(List<ScheduleEntry> entries) {
        for(int i = 0; i < entries.size(); i++)
            scheduleEntries.addLast(entries.get(i));
    }

    @Override
    public Station getDepartureStation() {
        ScheduleEntry departureEntry = scheduleEntries.get(0);
        return departureEntry.station();
    }

    @Override
    public Station getArrivalStation() {
        ScheduleEntry arrivalEntry = scheduleEntries.get(scheduleEntries.size()-1);
        return arrivalEntry.station();
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public Iterator<ScheduleEntry> listScheduleEntries() {
        return scheduleEntries.iterator();
    }

    @Override
    public Time getDepartureTime() {
        ScheduleEntry departureEntry = scheduleEntries.get(0);
        return departureEntry.time();
    }

    @Override
    public Time getArrivalTime() {
        ScheduleEntry arrivalEntry = scheduleEntries.get(scheduleEntries.size()-1);
        return arrivalEntry.time();
    }

    @Override
    public int compareTo(Train o) {
        return getNumber() - o.getNumber();
    }
}
