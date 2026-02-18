package system.base;

import java.io.Serializable;
/**
 * Represents the entity Rail that the User has access in Main.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface RailGet extends Serializable {
    /**
     * @return Name of the rail.
     */
    String getName();

    /**
     * @return Number of the stations that belong to the rail.
     */
    int numberOfStations();

}
