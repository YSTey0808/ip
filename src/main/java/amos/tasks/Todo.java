package amos.tasks;

/**
 * Represents a task without a specific date/time.
 *
 * <p>A Todo task only has a description and completion status.</p>
 */
public class Todo extends Task{
    /**
     * Creates a Todo task with a description.
     *
     * @param des the task description
     */
    public Todo(String des){
        super(des);
    }

    @Override
    public String writeTxt() {
        return "T |" + super.writeTxt();
    }

    @Override
    public String toString() {
        return "[T]" +  super.toString();
    }
}
