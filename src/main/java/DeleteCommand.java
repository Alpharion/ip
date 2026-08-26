/** Removes the task at a given 1-based list number from the task list. */
public class DeleteCommand extends Command {
    private final String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.size(), "delete");
        Task removedTask = tasks.remove(taskIndex);
        storage.save(tasks);
        ui.showMessage("Noted. I've removed this task:");
        ui.showIndentedMessage(removedTask);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
    }
}
