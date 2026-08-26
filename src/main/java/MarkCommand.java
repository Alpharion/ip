/** Marks the task at a given 1-based list number as done. */
public class MarkCommand extends Command {
    private final String arguments;

    public MarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.size(), "mark");
        tasks.get(taskIndex).markAsDone();
        storage.save(tasks);
        ui.showMessage("Nice! I've marked this task as done:");
        ui.showIndentedMessage(tasks.get(taskIndex));
    }
}
