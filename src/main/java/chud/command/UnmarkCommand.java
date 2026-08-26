package chud.command;

import chud.exception.ChudException;
import chud.parser.Parser;
import chud.storage.Storage;
import chud.task.TaskList;
import chud.ui.Ui;

/** Marks the task at a given 1-based list number as not done. */
public class UnmarkCommand extends Command {
    private final String arguments;

    public UnmarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.size(), "unmark");
        tasks.get(taskIndex).markAsNotDone();
        storage.save(tasks);
        ui.showMessage("OK, I've marked this task as not done yet:");
        ui.showIndentedMessage(tasks.get(taskIndex));
    }
}
