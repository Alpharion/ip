/**
 * Signals that the user typed something the chatbot could not act on
 * (an unknown command, or a known command used incorrectly).
 */
public class ChudException extends Exception {
    public ChudException(String message) {
        super(message);
    }
}
