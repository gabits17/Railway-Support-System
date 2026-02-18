package system.data;

import system.base.Time;
import system.base.Train;

import java.io.Serializable;

/**
 * Entity that stores the information of a Train passing in a Station at a given Time.
 * It is a pair of data Time/Train.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface PassingTrain extends Serializable, Comparable<PassingTrain> {
    /**
     * @return Time associated.
     */
    Time time();

    /**
     * @return Train associated.
     */
    Train train();
}
