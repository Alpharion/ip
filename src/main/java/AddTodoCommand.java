/** Adds a Todo task with the given (not yet validated) description text. */
public class AddTodoCommand extends Command {
    private final String arguments;

    public AddTodoCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ChudException {
        String description = Parser.parseTodoDescription(arguments);
        tasks.add(new Todo(description));
        storage.save(tasks);
        showTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
    }
}
