package chud;

import chud.command.Command;
import chud.exception.ChudException;
import chud.parser.Parser;
import chud.storage.Storage;
import chud.task.TaskList;
import chud.ui.Ui;

public class Chud {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("data/chud.txt");
        TaskList tasks = new TaskList(storage.load());

        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (ChudException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }
}
