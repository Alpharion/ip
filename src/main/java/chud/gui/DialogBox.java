package chud.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * One chat bubble: a wrapped, padded {@link Label} inside an {@link HBox}, right-aligned for the
 * user and left-aligned for Chud. Colors come from the {@code user-bubble}/{@code chud-bubble}
 * style classes in {@code DarkTheme.css}, not inline styles, so the whole chat's palette stays
 * defined in one place.
 */
public class DialogBox extends HBox {
    private DialogBox(String message, boolean isUser) {
        Label label = new Label(message);
        label.setWrapText(true);
        label.setMaxWidth(320);
        label.getStyleClass().add(isUser ? "user-bubble" : "chud-bubble");

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
