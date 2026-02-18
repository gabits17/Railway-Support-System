package system;

import dataStructures.Comparator;
import system.data.PassingTrain;

/**
 * Comparator of PassingTrains.
 * Used for sorting the trains that pass through a station by time of passing, stored in a TreeSet.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public class PassingTrainComparator implements Comparator<PassingTrain> {
    @Override
    public int compare(PassingTrain o1, PassingTrain o2) {
        int res = o1.time().compareTo(o2.time());
        if(res == 0) res = o1.train().compareTo(o2.train());
        return res;
    }
}
