package system.base;

import dataStructures.*;
import system.data.ScheduleEntry;

import java.io.Serial;

public class RailClass implements RailGet, Rail {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Name of the rail.
     */
    private final String name;

    /**
     * Set of stations that belong to the rail.
     */
    private final SearchableDoubleList<StationGet> stations;

    /**
     * Ordered set of trains going through the normal direction.
     */
    private final OrderedDictionary<Time,TrainGet> trainsNormalDir;

    /**
     * Ordered set of trains going through the inverse direction.
     */
    private final OrderedDictionary<Time,TrainGet> trainsInverseDir;

    /**
     * List of all trains, not in order.
     * Useful to access all trains "randomly" and remove
     * them from the stations where they pass.
     */
    private final List<Train> allTrains;

    /**
     * Terminal stations of the rail.
     */
    private Station term1, term2;

    /**
     * Constructor of an entity Rail.
     * @param name Name of the rail.
     */
    public RailClass(String name) {
        this.name = name;
        term1 = term2 = null;
        trainsNormalDir = new AVLTree<>();
        trainsInverseDir = new AVLTree<>();
        allTrains = new DoubleList<>();
        stations = new SearchableDoubleListClass<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int numberOfStations() {
        return stations.size();
    }

    @Override
    public void insertStation(Station station) {
        stations.addLast(station);
    }

    @Override
    public Iterator<StationGet> listStations(Station departure) {
        boolean direction = isNormalDir(departure);
        TwoWayIterator<StationGet> it = stations.twoWayIterator();
        if(!direction) {
            it.fullForward();
            List<StationGet> reversed = new DoubleList<>();
            while(it.hasPrevious()) reversed.addLast(it.previous());
            return reversed.twoWayIterator();
        }
        return it;
    }

    @Override
    public void setTerminals() {
        term1 = (Station) stations.get(0);
        term2 = (Station) stations.get(numberOfStations() - 1);
    }

    @Override
    public Station getTerminal1() {
        return term1;
    }

    @Override
    public Station getTerminal2() {
        return term2;
    }
    
    @Override
    public void insertTrain(List<ScheduleEntry> schInputs, int train) {
        Train t = new TrainClass(schInputs,train);
        ScheduleEntry e = schInputs.get(0);
        Time depTime = e.time();
        Station depStation = e.station();
        if(isNormalDir(depStation)) trainsNormalDir.insert(depTime, t);
        else trainsInverseDir.insert(depTime, t);
        insertTrainInStations(schInputs,t);
        allTrains.addLast(t);
    }

    @Override
    public void removeTrain(String station, String hour, String minute) {
        Time time = new TimeClass(hour,minute);
        Station s = getStation(station);
        Train t;
        if(isNormalDir(s)) {
            t = (Train)trainsNormalDir.find(time); trainsNormalDir.remove(time);
        } else {
            t = (Train)trainsInverseDir.find(time); trainsInverseDir.remove(time);
        }
        removeTrainFromStations(t);
        allTrains.remove(t);
    }

    @Override
    public Iterator<TrainGet> listTrainsDepartingAt(Station dep) {
        Iterator<Entry<Time,TrainGet>> it;
        if(isNormalDir(dep)) it = trainsNormalDir.iterator();
        else it = trainsInverseDir.iterator();

        return new VIterator<>(it);
    }

    @Override
    public boolean isTerminalStation(Station s) {
        return s.equals(term1) || s.equals(term2);
    }

    @Override
    public Train getBestSchedule(Station departure, Station arrival, String hour, String min) {
        Time expect = new TimeClass(hour, min), best = null;
        Train trainWithBestSch = null;

        boolean direction = findRouteDirection(departure, arrival);
        Iterator<Entry<Time, TrainGet>> it;
        if (direction) it = trainsNormalDir.iterator();
        else it = trainsInverseDir.iterator();

        while (it.hasNext()) {
            Train t = (Train)it.next().getValue();
            Iterator<ScheduleEntry> schIt = t.listScheduleEntries();
            boolean foundDep = false, end = false;
            while(!end && schIt.hasNext()) {
                ScheduleEntry e = schIt.next();
                Station s = e.station();
                Time arrvTime = e.time();
                if(!foundDep) foundDep = s.equals(departure);
                else if(s.equals(arrival)) {
                    if(arrvTime.compareTo(expect) == 0) return t;
                    if((arrvTime.compareTo(expect) < 0) && (best == null || arrvTime.compareTo(best) > 0)) {
                        best = arrvTime; trainWithBestSch = t;
                    }
                    end = true;
                }
            }
        }
        return trainWithBestSch;
    }

    @Override
    public Train findSchedule(String dep, String hour, String min) {
        Time toFind = new TimeClass(hour,min);
        Station departure = getStation(dep);
        Iterator<Entry<Time,Train>> it;

        if (isNormalDir(departure)) return (Train) trainsNormalDir.find(toFind);
        else return (Train) trainsInverseDir.find(toFind);
    }

    @Override
    public void removesAllTrainsFromStations() {
        Iterator<Train> it = allTrains.iterator();
        while(it.hasNext()) {
            Train train = it.next();
            Iterator<ScheduleEntry> schIt = train.listScheduleEntries();
            while (schIt.hasNext()) {
                ScheduleEntry p = schIt.next();
                Station s = p.station();
                s.removeTrain(train);
            }
        }
    }

    @Override
    public boolean isNormalDir(Station departure) {
        return departure.equals(term1);
    }


    /***** PRIVATE METHODS *****

     /*
     * Finds station named <name> that belongs to the rail.
     * @param name Name of the station.
     * @return The station object if found; Null otherwise.
     */
     private Station getStation(String name) {
         Station s = new StationClass(name);
         return (Station) stations.findEquals(s);
     }

    /**
     * Finds the direction of the route for the MH command.
     * @param departure Departure station.
     * @param arrival Arrival station.
     * @return True if direction is dep-arv; False if direction is arv-dep.
     */
    private boolean findRouteDirection(Station departure, Station arrival) {
        int dep_idx = stations.find(departure);
        int arr_idx = stations.find(arrival);
        return dep_idx < arr_idx;
    }

    /**
     * Inserts the train in every station of the rail.
     * @param schInputs Schedule entries of the schedule that the train respects.
     * @param train Train being inserted.
     */
    private void insertTrainInStations(List<ScheduleEntry> schInputs, Train train) {
        Iterator<ScheduleEntry> it = schInputs.iterator();
        while(it.hasNext()) {
            ScheduleEntry e = it.next();
            Station s = e.station();
            Time t = e.time();
            s.insertTrain(t,train);
        }
    }

    /**
     * Removes the train from every station of the rail.
     * @param train Train being removed.
     */
    private void removeTrainFromStations(Train train) {
        Iterator<StationGet> it = stations.iterator();
        while (it.hasNext()) {
            Station s = (Station) it.next();
            s.removeTrain(train);
        }
    }
}