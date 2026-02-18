package system;
import dataStructures.*;
import system.base.RailGet;
import system.base.StationGet;
import system.base.Train;
import system.base.TrainGet;
import system.data.PassingTrain;
import system.data.ScheduleEntry;
import system.exceptions.*;

import java.io.Serializable;

/**
 * System interface.
 * RailWayNetwork has sets of rails and stations.
 * Deals with all the functionalities of the program.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface RailWayNetwork extends Serializable {


    /**
     * Inserts a rail in the system.
     * @param railName Name of the rail being inserted.
     * @param stationsNames List of names of the stations that will be part of the rail.
     * @throws ExistentRailException When rail with name <railName> is already in the system.
     * @pre stationsNames.size() > 1
     */
    void insertRail(String railName, List<String> stationsNames)
            throws ExistentRailException;

    /**
     * Removes a rail of the system.
     * @param railName Name of the rail being removed.
     * @throws NonExistentRailException When rail with name <railName> is not on the system.
     */
    void removeRail(String railName)
            throws NonExistentRailException;

    /**
     * Lists every station of a rail.
     *
     * @param railName Name of the rail.
     * @return Iterator of stations of the rail.
     * @throws NonExistentRailException When rail with name <railName> is already in the system.
     */
    Iterator<StationGet> listRailStations(String railName)
            throws NonExistentRailException;

    /**
     * Saves an entry of the schedule: pair of station-time.
     * @param station Station of the pair.
     * @param hour Hour of the pair.
     * @param min Min of the pair.
     * @param entries List where the entries are being saved.
     */
    void saveSchEntry(String station, String hour, String min, List<ScheduleEntry> entries);

    /**
     * Inserts a schedule in rail named <rail>.
     * @param rail Name of the rail.
     * @param entries List of entries station-time of the schedule.
     * @param train Number of the train of the schedule.
     * @throws NonExistentRailException When rail with name <railName> is already in the system.
     * @throws InvalidScheduleException When the schedule isn't valid.
     */
    void insertSchedule(String rail, List<ScheduleEntry> entries, int train)
            throws NonExistentRailException, InvalidScheduleException;

    /**
     * Removes a schedule of the rail named <rail>.
     * @param rail Name of the rail.
     * @param station Departure station of the schedule.
     * @param hour Departure hour of the schedule.
     * @param minute Departure minute of the schedule.
     * @throws NonExistentRailException When rail with name <railName> is already in the system.
     * @throws NonExistentScheduleException When schedule with departure station,hour,minute isn't in the rail.
     */
    void removeSchedule(String rail, String station, String hour, String minute)
            throws NonExistentRailException, NonExistentScheduleException;

    /**
     * Lists every schedule of rail named <rail> that departs at station named <station>.
     *
     * @param rail    Name of the rail.
     * @param station Name of the station.
     * @return Schedule's departing at station named <station> iterator.
     * @throws NonExistentRailException             When rail with name <railName> is already in the system.
     * @throws NonExistentDepartureStationException When station named <station> isn't a departure station of the rail.
     */
    Iterator<TrainGet> consultRailSchedule(String rail, String station)
            throws NonExistentRailException , NonExistentDepartureStationException;

    /**
     * Gets the train of the best schedule for a trip between stations named <dep> and <arrv>.
     *
     * @param rail Name of the rail.
     * @param dep  Name of the station of departure of the trip.
     * @param arrv Name of the station of arrival of the trip.
     * @param hour Hour of the expected time.
     * @param min  Minute of the expected time.
     * @return Train object that contains the best schedule for the given route.
     */
    Train getBestTrain(String rail, String dep, String arrv, String hour, String min)
            throws NonExistentRailException, NonExistentDepartureStationException, ImpossibleRouteException;

    /**
     * Lists every rail that the station named <stationName> belongs to.
     *
     * @param stationName Name of the station.
     * @return Iterator of rails that the station belongs to.
     * @throws NonExistentStationException When the station doesn't exist.
     */
    Iterator<RailGet> listStationRails(String stationName) throws NonExistentStationException;

    /**
     * Lists every train that passes through the station named <stationName>.
     *
     * @param station Name of the station.
     * @return Iterator of trains that pass through the station.
     * @throws NonExistentStationException When the station doesn't exist in the system.
     */
    Iterator<PassingTrain> listTrains(String station) throws NonExistentStationException;
}
