package system.base;
import dataStructures.Iterator;
import system.data.PassingTrain;

/**
 * Represents the entity Station.
 * Has a set of rails to where it belongs, a set of
 * trains that pass through it, and a name.
 * Deals with the modifications of the entity Station.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface Station extends StationGet {

    void insertTrain(Time time, Train train);

    /**
     * Inserts rail <r> to station.
     * It means the station belongs to that rail.
     * @param r Rail being inserted.
     */
    void insertRail(Rail r);

    /**
     * Removes rail <r> from the station.
     * It means the rail was removed from the system.
     * @param r Rail being removed.
     */
    void removeRail(Rail r);

    /**
     * Lists every rail that it belongs to.
     * @return Iterator of the rails that it belongs to.
     */
    Iterator<RailGet> listRails();

    /**
     * Lists every Train that pass through it.
     * The trains are sorted by time of passing.
     * @return Iterator of the trains that pass in the station.
     */
    Iterator<PassingTrain> listTrains();

    /**
     * Removes a train from the station.
     * @param t Train being removed.
     */
    void removeTrain(Train t);
}
