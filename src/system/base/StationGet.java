package system.base;

import java.io.Serializable;
/**
 * Represents the entity Station that the User has access in Main.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public interface StationGet extends Serializable {

    /**
     * @return Name of the station,
     */
    String getName();

    /**
     * @return Number of rails that the station belongs to.
     */
    int numberOfRails();

}
