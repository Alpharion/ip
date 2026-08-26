/**
 * Makes sense of a raw line of user input: splits it into a command word and arguments, and
 * further interprets each command's arguments into the well-typed pieces its handler needs
 * (a task index; a todo's description; a deadline's description and {@code /by} date/time; an
 * event's description and {@code /from}/{@code /to} date/times; an {@code on} query date).
 *
 * <p>Every parsing failure is reported as a {@link ChudException} carrying a specific,
 * correctable message, so the caller can show it to the user without knowing the reason itself.
 */
public class Parser {
    /** Returns the command word (e.g. "todo") from a raw input line. */
    public static String parseCommandWord(String input) {
        int spaceIndex = input.indexOf(' ');
        return spaceIndex == -1 ? input : input.substring(0, spaceIndex);
    }

    /** Returns the trimmed arguments text (e.g. "borrow book") from a raw input line. */
    public static String parseArguments(String input) {
        int spaceIndex = input.indexOf(' ');
        return spaceIndex == -1 ? "" : input.substring(spaceIndex + 1).trim();
    }

    /**
     * Parses a 1-based task number typed by the user and returns the matching 0-based list index,
     * throwing a ChudException with a specific explanation for every way the input can be invalid.
     */
    public static int parseTaskIndex(String arguments, int taskCount, String commandWord) throws ChudException {
        if (arguments.isEmpty()) {
            throw new ChudException("Tell me which task number, e.g. " + commandWord + " 2");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            throw new ChudException("'" + arguments + "' is not a valid task number.");
        }
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new ChudException("There is no task number " + taskNumber + ". "
                    + (taskCount == 0 ? "Your task list is empty." : "You have " + taskCount + " task(s)."));
        }
        return taskNumber - 1;
    }

    /** Validates a todo's arguments and returns its description. */
    public static String parseTodoDescription(String arguments) throws ChudException {
        if (arguments.isEmpty()) {
            throw new ChudException("The description of a todo cannot be empty. Try: todo borrow book");
        }
        return arguments;
    }

    /** A deadline's parsed description and {@code /by} date/time. */
    public static class DeadlineArgs {
        public final String description;
        public final TaskDateTime by;

        private DeadlineArgs(String description, TaskDateTime by) {
            this.description = description;
            this.by = by;
        }
    }

    /** Parses a deadline's arguments (description and {@code /by} date/time). */
    public static DeadlineArgs parseDeadlineArgs(String arguments) throws ChudException {
        int byIndex = arguments.indexOf("/by ");
        if (byIndex == -1) {
            throw new ChudException("A deadline needs a '/by' date/time. "
                    + "Try: deadline return book /by 2019-10-15 1800");
        }
        String description = arguments.substring(0, byIndex).trim();
        String byText = arguments.substring(byIndex + 4).trim();
        if (description.isEmpty()) {
            throw new ChudException("The description of a deadline cannot be empty. "
                    + "Try: deadline return book /by 2019-10-15 1800");
        }
        if (byText.isEmpty()) {
            throw new ChudException("The '/by' date/time of a deadline cannot be empty. "
                    + "Try: deadline return book /by 2019-10-15 1800");
        }
        try {
            return new DeadlineArgs(description, TaskDateTime.parse(byText));
        } catch (IllegalArgumentException e) {
            throw new ChudException(e.getMessage());
        }
    }

    /** An event's parsed description and {@code /from}/{@code /to} date/times. */
    public static class EventArgs {
        public final String description;
        public final TaskDateTime from;
        public final TaskDateTime to;

        private EventArgs(String description, TaskDateTime from, TaskDateTime to) {
            this.description = description;
            this.from = from;
            this.to = to;
        }
    }

    /** Parses an event's arguments (description and {@code /from}/{@code /to} date/times). */
    public static EventArgs parseEventArgs(String arguments) throws ChudException {
        int fromIndex = arguments.indexOf("/from ");
        int toIndex = arguments.indexOf("/to ");
        if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
            throw new ChudException(
                    "An event needs both '/from' and '/to' date/times, in that order. "
                            + "Try: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
        }
        String description = arguments.substring(0, fromIndex).trim();
        String fromText = arguments.substring(fromIndex + 6, toIndex).trim();
        String toText = arguments.substring(toIndex + 4).trim();
        if (description.isEmpty()) {
            throw new ChudException("The description of an event cannot be empty. "
                    + "Try: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
        }
        if (fromText.isEmpty() || toText.isEmpty()) {
            throw new ChudException("The '/from' and '/to' date/times of an event cannot be empty. "
                    + "Try: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
        }
        try {
            return new EventArgs(description, TaskDateTime.parse(fromText), TaskDateTime.parse(toText));
        } catch (IllegalArgumentException e) {
            throw new ChudException(e.getMessage());
        }
    }

    /** Parses the date typed after {@code on} into the date/time to filter tasks by. */
    public static TaskDateTime parseOnDate(String arguments) throws ChudException {
        if (arguments.isEmpty()) {
            throw new ChudException("Tell me which date, e.g. on 2019-10-15");
        }
        try {
            return TaskDateTime.parse(arguments);
        } catch (IllegalArgumentException e) {
            throw new ChudException(e.getMessage());
        }
    }
}
