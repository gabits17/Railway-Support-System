package system;
import dataStructures.*;
import system.base.*;
import system.data.PassingTrain;
import system.data.ScheduleEntry;
import system.data.ScheduleEntryClass;
import system.exceptions.*;

import java.io.Serial;

public class RailWayNetworkClass implements RailWayNetwork {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Sets of rails in the system.
     */
    private final Dictionary<String,Rail> rails;

    /**
     * Sets of stations in the system.
     */
    private final Dictionary<String, Station> stations;

    /**
     * Constructor of the system.
     */
    public RailWayNetworkClass() {
        rails = new SepChainHashTable<>();
        stations = new SepChainHashTable<>();
    }

    @Override
    public void insertRail(String railName, List<String> stationsNames) throws ExistentRailException {
        Rail r = getRail(railName);
        if(r != null)
            throw new ExistentRailException();

        Rail rail = new RailClass(railName);
        rails.insert(railName.toLowerCase(),rail);
        addStationsToRail(rail,stationsNames);
    }


    @Override
    public void removeRail(String railName) throws NonExistentRailException {
        Rail r = getRail(railName);
        if(r == null)
            throw new NonExistentRailException();
        r.removesAllTrainsFromStations();
        removeRailFromStations(r);

        rails.remove(railName.toLowerCase());
    }

    @Override
    public Iterator<StationGet> listRailStations(String railName) throws NonExistentRailException {
        Rail r = getRail(railName);
        if(r == null)
            throw new NonExistentRailException();
        Station dep = r.getTerminal1();
        return r.listStations(dep);
    }

    @Override
    public void saveSchEntry(String stationName, String hour, String min, List<ScheduleEntry> schInput) {
        Station s = getStation(stationName);
        Time t = new TimeClass(hour,min);
        schInput.addLast(new ScheduleEntryClass(s,t));
    }

    @Override
    public void insertSchedule(String railName, List<ScheduleEntry> schInput, int trainNumber)
            throws NonExistentRailException, InvalidScheduleException {
        Rail r = getRail(railName);
        if(r == null)
            throw new NonExistentRailException();
        validateSchedule(r,schInput);
        r.insertTrain(schInput,trainNumber);
    }

    @Override
    public void removeSchedule(String railName, String stationName, String hour, String minute)
            throws NonExistentRailException, NonExistentScheduleException {
        Rail r = getRail(railName);
        if(r == null)
            throw new NonExistentRailException();
        if(r.findSchedule(stationName,hour,minute) == null)
            throw new NonExistentScheduleException();
        r.removeTrain(stationName,hour,minute);
    }

    @Override
    public Iterator<TrainGet> consultRailSchedule(String railName, String stationName)
            throws NonExistentRailException, NonExistentDepartureStationException {
        Rail r = getRail(railName);
        if(r == null)
            throw new NonExistentRailException();
        Station s = getStation(stationName);
        if(!r.isTerminalStation(s))
            throw new NonExistentDepartureStationException();
        return r.listTrainsDepartingAt(s);
    }


    @Override
    public Train getBestTrain(String railName, String depName, String arrvName, String hour, String min)
            throws NonExistentRailException, NonExistentDepartureStationException, ImpossibleRouteException {
        Rail r = getRail(railName);
        if(r == null)
            throw new NonExistentRailException();

        Station departure = getStation(depName);
        Station arrival = getStation(arrvName);

        if(departure == null)
            throw new NonExistentDepartureStationException();
        if(arrival == null)
            throw new ImpossibleRouteException();

        Train best = r.getBestSchedule(departure,arrival,hour,min);
        if(best == null) throw new ImpossibleRouteException();

        return best;
    }

    @Override
    public Iterator<RailGet> listStationRails(String stationName)
            throws NonExistentStationException {
        Station s = getStation(stationName);
        if (s == null) throw new NonExistentStationException();
        return s.listRails();
    }

    @Override
    public Iterator<PassingTrain> listTrains(String stationName)
            throws NonExistentStationException {
        Station s = getStation(stationName);
        if (s == null) throw new NonExistentStationException();

        return s.listTrains();
    }

    /* **** PRIVATE METHODS ****/
    /**
     * @param railName Name of the rail.
     * @return Rail with name <railName> if it exists. Null otherwise.
     */
    private Rail getRail(String railName) {
        return rails.find(railName.toLowerCase());
    }

    /**
     * @param stationName Name of the station.
     * @return Station with name <stationName> if it exists. Null otherwise.
     */
    private Station getStation(String stationName) {
        return stations.find(stationName.toLowerCase());
    }


    /**
     * Adds a list of stations to a rail.
     * If a station is already in the system, it just adds the station to the r.
     * If not, it creates a new station.
     * @param r – The r where the stations are being inserted.
     * @param stationsNames – List of the names of the stations.
     * @pre stationsNames.size() > 1
     * */
    private void addStationsToRail(Rail r, List<String> stationsNames) {
        for(int i = 0; i < stationsNames.size(); i++) {
            String stationName = stationsNames.get(i);
            Station s = getStation(stationName);

            if(s == null) {
                s = new StationClass(stationName);
                stations.insert(stationName.toLowerCase(),s);
            }
            s.insertRail(r);
            r.insertStation(s);
        }
        r.setTerminals();
    }

    /**
     * Removes a rail from every station that belonged to it.
     * If a station only belongs to this rail, that station is also removed from the system.
     * @param r Rail being removed.
     */
    private void removeRailFromStations(Rail r) {
        Station dep = r.getTerminal1();
        Iterator<StationGet> it = r.listStations(dep);
        while (it.hasNext()) {
            Station s = (Station) it.next();
            s.removeRail(r);
            if(s.numberOfRails() == 0) stations.remove(s.getName().toLowerCase());
        }
    }

    /**
     * Checks if the list of schedule entries is valid for the insertion of a new schedule.
     * @param r Rail where the schedule is being inserted.
     * @param schInput List of schedule entries.
     */
    private void validateSchedule(Rail r, List<ScheduleEntry> schInput)
            throws InvalidScheduleException {

        Station departure = schInput.get(0).station();
        if (!r.isTerminalStation(departure)) throw new InvalidScheduleException();

        if (!isOvertakingAvoided(r, schInput)) throw new InvalidScheduleException();

        Iterator<ScheduleEntry> entriesIt = schInput.iterator();
        Iterator<StationGet> stationIt = r.listStations(departure);
        Time prev = entriesIt.next().time();

        while (entriesIt.hasNext()) {
            ScheduleEntry e = entriesIt.next();
            Time curr = e.time(); Station schStation = e.station();
            if (curr.compareTo(prev) <= 0) throw new InvalidScheduleException();
            prev = curr;

            boolean foundStation = false;
            while (stationIt.hasNext() && !foundStation){
                Station s = (Station) stationIt.next();
                if (s.equals(schStation)) foundStation = true;}

            if (!foundStation)
                throw new InvalidScheduleException();
        }
    }

    /**
     * Checks if the new train's schedule being analyzed overtakes another existing train.
     * @param r Rail where the new train will be inserted.
     * @param schInput List of schedule entries of the schedule of the train.
     * @return True if there is no conflict, false otherwise.
     */
    private boolean isOvertakingAvoided(Rail r, List<ScheduleEntry> schInput) {
        Station newDepStation = schInput.get(0).station();
        Station newArrvStation = schInput.get(schInput.size()-1).station();
        Time newDepTime = schInput.get(0).time();
        Time newArrvTime = schInput.get(schInput.size()-1).time();

        Iterator<TrainGet> trainsSameDir = r.listTrainsDepartingAt(newDepStation);
        while (trainsSameDir.hasNext()) {
            Train currTrain = (Train)trainsSameDir.next();

            Iterator<ScheduleEntry> newSchIt = schInput.iterator();
            Iterator<ScheduleEntry> schIt = currTrain.listScheduleEntries();
            int compDepTimes = currTrain.getDepartureTime().compareTo(newDepTime);
            int compArrvTimes = currTrain.getArrivalTime().compareTo(newArrvTime);
            ScheduleEntry newCurr = null; boolean departsLater = false; int c = 0;

            while (schIt.hasNext()) {
                ScheduleEntry schEntry = schIt.next();
                if (newSchIt.hasNext()) newCurr = newSchIt.next();
                if (newCurr != null && schEntry.station().equals(newCurr.station())) {
                    int compCurrTimes = schEntry.time().compareTo(newCurr.time());
                    if(c == 0 && compCurrTimes < 0)
                        departsLater = true;
                    c++;
                    if((compCurrTimes == 0) || (compCurrTimes < 0 && !departsLater) || (compCurrTimes > 0 && departsLater))
                        return false;
                }
            }
            if (currTrain.getArrivalStation().equals(newArrvStation))
                if (compDepTimes < 0 && compArrvTimes >= 0 || compDepTimes >= 0 && compArrvTimes <= 0) return false;
        }
        return true;
    }

}
