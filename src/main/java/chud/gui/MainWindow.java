package chud.gui;

import chud.command.Command;
import chud.exception.ChudException;
import chud.parser.Parser;
import chud.storage.Storage;
import chud.task.TaskList;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 * Controller for {@code MainWindow.fxml}: the scrollable column of chat bubbles, the text field
 * the user types into, and the send button. Each submitted line is parsed and executed exactly
 * as the console version does (see {@code chud.Chud}), except replies are collected via {@link
 * GuiUi} and shown as a bubble instead of printed.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private TaskList tasks;
    private Storage storage;
    private GuiUi ui;

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Wires this window to the chatbot's backend, and shows its welcome message. */
    void init(TaskList tasks, Storage storage, GuiUi ui) {
        this.tasks = tasks;
        this.storage = storage;
        this.ui = ui;

        ui.showWelcome();
        dialogContainer.getChildren().add(DialogBox.getChudDialog(ui.flush()));
    }

    /** Called when the user presses Enter or clicks Send: shows the exchange as two bubbles. */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.isBlank()) {
            return;
        }
        userInput.clear();

        Command command = Parser.parse(input);
        try {
            command.execute(tasks, ui, storage);
        } catch (ChudException e) {
            ui.showError(e.getMessage());
        }
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getChudDialog(ui.flush()));

        if (command.isExit()) {
            // Give the user a moment to read the goodbye message before the window closes.
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
