package system.exceptions;

import java.io.Serial;

/**
 * This exception is thrown when an invalid schedule is being inserted in the rail.
 * Which means it includes stations that don't belong to the rail, or that the
 * stations are listed in the incorrect order, or that the time is not increasing, etc.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara DIas (67215) cso.dias@campus.fct.unl.pt
 */
public class InvalidScheduleException extends Exception {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;
    public InvalidScheduleException() {
        super();
    }
}
