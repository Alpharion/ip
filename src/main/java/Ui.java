import java.util.Scanner;

/**
 * Handles all interaction with the user: printing messages to the console in Chud's standard
 * format, and reading typed commands from standard input.
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "    ____________________________________________________________";
    private static final String BANNER = "  ____ _               _ \n"
            + " / ___| |__  _   _  __| |\n"
            + "| |   | '_ \\| | | |/ _` |\n"
            + "| |___| | | | |_| | (_| |\n"
            + " \\____|_| |_|\\__,_|\\__,_|\n";

    private final Scanner scanner = new Scanner(System.in);

    /** Prints the startup banner and greeting, framed by horizontal lines. */
    public void showWelcome() {
        System.out.println(HORIZONTAL_LINE);
        System.out.print(BANNER);
        System.out.println("     Hello! I'm Chud.");
        System.out.println("     What can I do for you?");
        System.out.println(HORIZONTAL_LINE);
    }

    /** Prints the horizontal-line separator that frames every response. */
    public void showLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /** Prints the farewell message, framed by horizontal lines. */
    public void showGoodbye() {
        System.out.println(HORIZONTAL_LINE);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(HORIZONTAL_LINE);
    }

    /** Prints one line of output at the standard message indent, e.g. "     Here are ...". */
    public void showMessage(Object content) {
        System.out.println("     " + content);
    }

    /** Prints one line of output at the deeper indent used to display a task under a message. */
    public void showIndentedMessage(Object content) {
        System.out.println("       " + content);
    }

    /** Prints an error message in Chud's standard "OOPS!!!" format. */
    public void showError(String message) {
        System.out.println("     OOPS!!! " + message);
    }

    /**
     * Reads the next line of typed input, trimmed of surrounding whitespace so that stray spaces
     * (e.g. "bye " or "  list") don't stop commands from being recognized. If input has run out
     * (e.g. piped input with no trailing "bye", or the user pressing Ctrl+D) this returns "bye"
     * so the program exits gracefully instead of crashing with a NoSuchElementException.
     */
    public String readCommand() {
        return scanner.hasNextLine() ? scanner.nextLine().trim() : "bye";
    }
}
