/** Adds an Event task with the given (not yet validated) "description /from ... /to ..." text. */
public class AddEventCommand extends Command {
    private final String arguments;

    public AddEventCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException {
        Parser.EventArgs eventArgs = Parser.parseEventArgs(arguments);
        tasks.add(new Event(eventArgs.description, eventArgs.from, eventArgs.to));
        storage.save(tasks);
        showTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
    }
}
