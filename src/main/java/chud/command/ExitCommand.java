package chud.command;

import chud.storage.Storage;
import chud.task.TaskList;
import chud.ui.Ui;

/** Says goodbye and signals the main loop to end the program. */
public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showMessage("Bye. Hope to see you again soon!");
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
