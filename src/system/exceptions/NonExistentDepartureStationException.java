package system.exceptions;

import java.io.Serial;

/**
 * This exception is thrown when a station, given by the user to be a departure station
 * of some specific rail in the system, is not.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara DIas (67215) cso.dias@campus.fct.unl.pt
 */
public class NonExistentDepartureStationException extends Exception {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;
    public NonExistentDepartureStationException() {
        super();
    }
}
