package system.base;

import dataStructures.*;
import system.data.ScheduleEntry;

/**
 * Represents the entity Rail.
 * Has a set of stations that belong to the rail,
 * two sets of trains that pass in this rail separated by direction, and a name.
 * Deals with all the functionalities regarding rails.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface Rail extends RailGet {
    /**
     * Inserts a station to the rail.
     * @param station Station being inserted.
     * @pre station is already in the system.
     */
    void insertStation(Station station);


    /**
     * Lists every station that belongs to the rail, given a direction.
     *
     * @param departure Departure station.
     * @return Rail station's iterator in normal direction if departure is terminal1;
     * in reversed direction if departure is terminal2.
     */
    Iterator<StationGet> listStations(Station departure);

    /**
     * Sets terminal stations to stations at index 0 and size - 1.
     */
    void setTerminals();

    /**
     * @return Terminal 1.
     * Terminal 1 is the first station inserted in the input while creating the rail.
     */
    Station getTerminal1();

    /**
     * @return Terminal 2.
     * Terminal 2 is the last station inserted in the input while creating the rail.
     */
    Station getTerminal2();

    /**
     * Inserts a schedule in the rail.
     * A schedule is a train that goes by the stations of the rail.
     * @param entries List of entries (pairs time-station) of the schedule.
     * @param train Number of the train.
     * @pre entries.size() >= 2
     */
    void insertTrain(List<ScheduleEntry> entries, int train);

    /**
     * Removes a specific schedule from the rail.
     * @param station Name of the departure station of that schedule.
     * @param hour Hour (hh) of the departure time of that schedule.
     * @param minute Minute (mm) of the departure time of that schedule.
     */
    void removeTrain(String station, String hour, String minute) ;

    /**
     * Lists every schedule that departs at station named <station> that has been inserted in the rail.
     *
     * @param station Name of the departure station.
     * @return Station departure schedule's iterator.
     */
    Iterator<TrainGet> listTrainsDepartingAt(Station station);

    /**
     * Checks if station named <station> is a terminal station.
     * @param station Name of the station being analyzed.
     * @return True if it is, false otherwise.
     */
    boolean isTerminalStation(Station station);

    /**
     * Calculates the best schedule that contains the best route from station named <dep> to station
     * named <arrv> until a given hour of arrival <hour>:<min>.
     * @param dep The departure station of the route. It does not require to be a terminal station.
     * @param arrv The arrival station of the route. It does not require to be a terminal station.
     * @param hour Hour (hh) of the expected time of arrival at station arrv.
     * @param min Minutes (mm) of the expected time of arrival at station arrv.
     * @return The best schedule given the conditions. Null if it doesn't exist.
     */
    Train getBestSchedule(Station dep, Station arrv, String hour, String min);

    /**
     * Finds the pair time-station of departure of a schedule to identify uniquely a schedule.
     *
     * @param station Station of departure.
     * @param hour    Hour (hh) of departure.
     * @param min     Minutes (mm) of departure.
     * @return The pair or null if the schedule doesn't exist.
     */
    Train findSchedule(String station, String hour, String min);

    /**
     * Removes all trains (schedules) from all stations of this rail.
     * This happens when the rail is removed from the system.
     */
    void removesAllTrainsFromStations();

    /**
     * Finds the direction of the train being inserted.
     * @param departure Departure station of the train (term1 or term2).
     * @return True if direction is term1-term2; False if direction is term2-term1.
     */
    boolean isNormalDir(Station departure);
}
