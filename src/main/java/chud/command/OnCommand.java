package chud.command;

import chud.exception.ChudException;
import chud.parser.Parser;
import chud.storage.Storage;
import chud.task.Task;
import chud.task.TaskDateTime;
import chud.task.TaskList;
import chud.ui.Ui;

import java.time.LocalDate;

/** Lists the deadlines/events occurring on a given (not yet validated) date/time text. */
public class OnCommand extends Command {
    private final String arguments;

    public OnCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException {
        TaskDateTime queryDateTime = Parser.parseOnDate(arguments);
        LocalDate date = queryDateTime.getDate();
        ui.showMessage("Here are the tasks occurring on " + queryDateTime + ":");
        int count = 0;
        for (Task task : tasks) {
            if (task.occursOn(date)) {
                count++;
                ui.showMessage(count + "." + task);
            }
        }
    }
}
