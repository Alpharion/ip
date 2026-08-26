/** Adds a Deadline task with the given (not yet validated) "description /by ..." text. */
public class AddDeadlineCommand extends Command {
    private final String arguments;

    public AddDeadlineCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException {
        Parser.DeadlineArgs deadlineArgs = Parser.parseDeadlineArgs(arguments);
        tasks.add(new Deadline(deadlineArgs.description, deadlineArgs.by));
        storage.save(tasks);
        showTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
    }
}
