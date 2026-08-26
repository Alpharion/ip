package chud.command;

import chud.exception.ChudException;
import chud.parser.Parser;
import chud.storage.Storage;
import chud.task.Task;
import chud.task.TaskList;
import chud.ui.Ui;

/** Lists the tasks whose description contains a given (not yet validated) keyword. */
public class FindCommand extends Command {
    private final String arguments;

    public FindCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException {
        String keyword = Parser.parseFindKeyword(arguments);
        ui.showMessage("Here are the matching tasks in your list:");
        int count = 0;
        for (Task task : tasks) {
            if (task.matchesKeyword(keyword)) {
                count++;
                ui.showMessage(count + "." + task);
            }
        }
    }
}
