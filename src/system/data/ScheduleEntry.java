package system.data;

import system.base.Station;
import system.base.Time;

import java.io.Serializable;
/**
 * Entity that stores an entry in a schedule.
 * It is a pair of data Time/Station that represents the time
 * when a train passes through the station in a schedule.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface ScheduleEntry extends Serializable, Comparable<ScheduleEntry> {
    /**
     * @return Station associated.
     */
    Station station();

    /**
     * @return Time associated.
     */
    Time time();
}
