package chud.task;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The list of tasks the chatbot is tracking. Wraps the underlying storage collection so callers
 * work through named task-list operations (add/get/remove/size) instead of reaching into a raw
 * {@code ArrayList} directly.
 */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /** Creates a task list backed by the given tasks, e.g. ones just loaded from disk. */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
