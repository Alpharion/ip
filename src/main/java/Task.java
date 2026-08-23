public class Task {
    protected String description;
    protected boolean isDone;
    protected String taskType;
    protected String by;
    protected String from;
    protected String to;

    private Task(String description, String taskType) {
        this.description = description;
        this.isDone = false;
        this.taskType = taskType;
    }

    public static Task createTodo(String description) {
        return new Task(description, "T");
    }

    public static Task createDeadline(String description, String by) {
        Task task = new Task(description, "D");
        task.by = by;
        return task;
    }

    public static Task createEvent(String description, String from, String to) {
        Task task = new Task(description, "E");
        task.from = from;
        task.to = to;
        return task;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        String taskLine = "[" + taskType + "][" + getStatusIcon() + "] " + description;
        if (taskType.equals("D")) {
            taskLine += " (by: " + by + ")";
        } else if (taskType.equals("E")) {
            taskLine += " (from: " + from + " to: " + to + ")";
        }
        return taskLine;
    }
}
