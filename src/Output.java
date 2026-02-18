/**
 * List of possible feedbacks from the program.
 * @author Gabriela Silva (67286) gt.silva@campus.fct.unlt.pt
 * @author Clara Dias (67215) cso.dias@campus.fct.unl.pt
 */
public enum Output {
    RAIL_INSERTION_SUCCESS("Inserção de linha com sucesso.\n"),
    EXISTENT_RAIL("Linha existente.\n"),
    RAIL_REMOVAL_SUCCESS("Remoção de linha com sucesso.\n"),
    NONEXISTENT_RAIL("Linha inexistente.\n"),
    NONEXISTENT_STATION("Estação inexistente.\n"),
    SCHEDULE_CREATION_SUCCESS("Criação de horário com sucesso.\n"),
    INVALID_SCHEDULE("Horário inválido.\n"),
    NONEXISTENT_SCHEDULE("Horário inexistente.\n"),
    SCHEDULE_REMOVAL_SUCCESS("Remoção de horário com sucesso.\n"),
    NONEXISTENT_DEPARTURE("Estação de partida inexistente.\n"),
    IMPOSSIBLE_ROUTE("Percurso impossível.\n"),
    APP_TERMINATED("Aplicação terminada.\n"),
    TRAIN("Comboio %d %02d:%02d\n"),
    INFO("%s %02d:%02d\n");

    /**
     * String of the feedback.
     */
    private final String out;

    /**
     * Constructor of the output.
     * @param out String of the ouput.
     */
    Output(String out) {
        this.out = out;
    }

    /**
     * Prints the output.
     * @return The output's string.
     */
    public String printOutput() {
        return out;
    }
}
