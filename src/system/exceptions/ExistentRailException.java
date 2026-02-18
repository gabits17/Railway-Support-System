package system.exceptions;

import java.io.Serial;

/**
 * This exception is thrown when a rail already exists in the system with the same name given by the user.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara DIas (67215) cso.dias@campus.fct.unl.pt
 */
public class ExistentRailException extends Exception {
    /**
     * Serial Version UID of the Class
     */
    @Serial
    private static final long serialVersionUID = 0L;
    public ExistentRailException() {
        super();
    }
}
