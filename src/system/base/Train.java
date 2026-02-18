package system.base;

import dataStructures.Iterator;
import system.data.ScheduleEntry;

import java.io.Serializable;
/**
 * Represents the entity Train.
 * Has a unique number as an ID and a list of entries that form a schedule: Schedule entries.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface Train extends TrainGet, Comparable<Train> {

    /**
     * @return Time of departure of the train.
     */
    Time getDepartureTime();

    /**
     * @return Time of arrival of the train.
     */
    Time getArrivalTime();

    /**
     * @return Station of departure of the train.
     */
    Station getDepartureStation();

    /**
     * @return Station of arrival of the train.
     */
    Station getArrivalStation();
}
