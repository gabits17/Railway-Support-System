import system.exceptions.NoCommandException;

/**
 * List of available commands of the program.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public enum Command{
    IL, RL, CL, CE, IH, RH, CH, LC, MH, TA;

    public static Command fromString(String option) throws NoCommandException {
        for (Command command : values())
            if (command.name().equalsIgnoreCase(option))
                return command;
        throw new NoCommandException();
    }
}
