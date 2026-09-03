package chud.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * One chat bubble: a wrapped, padded {@link Label} inside an {@link HBox}, right-aligned and
 * blue for the user, left-aligned and grey for Chud.
 */
public class DialogBox extends HBox {
    private static final String USER_STYLE = "-fx-background-color: #4a90d9; -fx-text-fill: white; "
            + "-fx-background-radius: 12; -fx-padding: 8 12 8 12;";
    private static final String CHUD_STYLE = "-fx-background-color: #e5e5ea; -fx-text-fill: black; "
            + "-fx-background-radius: 12; -fx-padding: 8 12 8 12;";

    private DialogBox(String message, boolean isUser) {
        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(320);
        label.setStyle(isUser ? USER_STYLE : CHUD_STYLE);

        setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);
        setSpacing(8);
        getChildren().add(label);
    }

    /** Creates a right-aligned bubble for something the user typed. */
    public static DialogBox getUserDialog(String message) {
        return new DialogBox(message, true);
    }

    /** Creates a left-aligned bubble for one of Chud's replies. */
    public static DialogBox getChudDialog(String message) {
        return new DialogBox(message, false);
    }
}
