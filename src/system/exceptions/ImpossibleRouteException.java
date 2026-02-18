package system.exceptions;

import java.io.Serial;

/**
 * This exception is thrown when the route between two stations until a given expected time is impossible.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara DIas (67215) cso.dias@campus.fct.unl.pt
 */
public class ImpossibleRouteException extends Exception {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;
    public ImpossibleRouteException() {
        super();
    }
}
