/**
 * The set of command words the chatbot understands. UNKNOWN represents any
 * other word the user might type.
 */
public enum Command {
    LIST,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    BYE,
    UNKNOWN;

    /**
     * Matches a typed command word (exact, lowercase, e.g. "todo") to its Command,
     * or UNKNOWN if it doesn't match any recognized command word.
     */
    public static Command fromCommandWord(String commandWord) {
        for (Command command : values()) {
            if (command != UNKNOWN && command.name().toLowerCase().equals(commandWord)) {
                return command;
            }
        }
        return UNKNOWN;
    }
}
