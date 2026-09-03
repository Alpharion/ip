package chud.gui;

import chud.ui.Ui;

/**
 * A {@link Ui} that buffers Chud's replies as plain text instead of printing them to the
 * console, so {@link MainWindow} can show them as a chat bubble. Every {@code Command} calls the
 * same {@code showMessage}/{@code showIndentedMessage}/{@code showError} methods either way --
 * only where the text ends up differs.
 */
public class GuiUi extends Ui {
    private final StringBuilder buffer = new StringBuilder();

    @Override
    public void showWelcome() {
        showMessage("Hello! I'm Chud.", "What can I do for you?");
    }

    @Override
    public void showLine() {
        // No-op: the console's horizontal-line separator has no GUI equivalent.
    }

    @Override
    public void showMessage(Object... contents) {
        for (Object content : contents) {
            appendLine(String.valueOf(content));
        }
    }

    @Override
    public void showIndentedMessage(Object content) {
        appendLine(String.valueOf(content));
    }

    @Override
    public void showError(String message) {
        appendLine("OOPS!!! " + message);
    }

    private void appendLine(String line) {
        if (buffer.length() > 0) {
            buffer.append("\n");
        }
        buffer.append(line);
    }

    /** Returns everything buffered since the last {@link #flush}, as one multi-line reply. */
    public String flush() {
        String text = buffer.toString();
        buffer.setLength(0);
        return text;
    }
}
