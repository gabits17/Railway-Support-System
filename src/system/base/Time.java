package system.base;

import java.io.Serializable;
/**
 * Represents the entity Time.
 * Has an hour and a minute associated.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface Time extends Comparable<Time>, Serializable {
    /**
     * @return Hour (hh) of the time.
     */
    int getHour();

    /**
     * @return Minutes (mm) of the time.
     */
    int getMin();
}
