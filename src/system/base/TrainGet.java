package system.base;

import dataStructures.Iterator;
import system.data.ScheduleEntry;

import java.io.Serializable;

/**
 * Represents the entity Train that the User has access in Main.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface TrainGet extends Serializable {
    /**
     * @return Number of the train that works through this schedule.
     */
    int getNumber();

    /**
     * Lists every entry of the schedule.
     * @return Iterator of the entries of the schedule.
     */
    Iterator<ScheduleEntry> listScheduleEntries();
}
