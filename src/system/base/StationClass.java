package system.base;

import dataStructures.*;
import system.PassingTrainComparator;
import system.data.PassingTrain;
import system.data.PassingTrainClass;

import java.io.Serial;

public  class StationClass implements StationGet, Station {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;

    /**
     * Name of the Station
     */
    private final String name;

    /**
     * Set of rails that the station belongs to
     */
    private final Dictionary<String,RailGet> rails;

    /**
     * Set of trains that pass through this station
     */
    private final TreeSet<PassingTrain> trains;

    public StationClass(String name) {
        this.name = name;
        rails = new OrderedDoubleList<>();
        trains = new TreeSetClass<>(new PassingTrainComparator());

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void insertTrain(Time time, Train train) {
        trains.insert(new PassingTrainClass(time, train));
    }

    @Override
    public void insertRail(Rail r) {
        rails.insert(r.getName(), r);
    }

    @Override
    public void removeRail(Rail r) {
        rails.remove(r.getName());
    }

    @Override
    public int numberOfRails() {
        return rails.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StationClass that = (StationClass) o;
        return name.equals(that.name);
    }

    @Override
    public Iterator<RailGet> listRails() {
        Iterator <Entry<String,RailGet>> it = rails.iterator();
        return new VIterator<>(it);
    }

    @Override
    public Iterator<PassingTrain> listTrains() {
        return trains.iterator();
    }

    @Override
    public void removeTrain(Train t) {
        boolean end = false;
        Iterator<PassingTrain> it = trains.iterator();
        while(!end && it.hasNext()) {
            PassingTrain pt = it.next();
            if (pt.train().equals(t)) {
                trains.remove(pt);
                end = true;
            }
       }
    }
}
