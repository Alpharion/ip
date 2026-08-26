/**
 * A single user command, already parsed and ready to run against the task list. Each concrete
 * Command knows how to execute itself -- mutating the task list, saving to disk via Storage,
 * and/or printing to the user via Ui, as appropriate -- and whether running it should end the
 * program.
 */
public abstract class Command {
    /** Runs this command against the given task list, printing to ui and saving via storage. */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException;

    /** Returns true if this command should end the program after it runs. */
    public boolean isExit() {
        return false;
    }

    /** Prints the standard "task added" confirmation shared by the three add-task commands. */
    protected static void showTaskAdded(Ui ui, Task task, int taskCount) {
        ui.showMessage("Got it. I've added this task:");
        ui.showIndentedMessage(task);
        ui.showMessage("Now you have " + taskCount + " tasks in the list.");
    }
}
