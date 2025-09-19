package amos.tasks;

import java.util.ArrayList;
import java.util.List;

import amos.exceptions.AmosDuplicateException;

/**
 * Represents a list of tasks.
 *
 * <p>This class provides methods to add, delete, and retrieve tasks,
 * as well as get the size of the list.</p>
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a task list with pre-loaded tasks.
     *
     * @param loadedTasks the list of tasks to initialize with
     */
    public TaskList(List<Task> loadedTasks) {
        tasks = loadedTasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param t the task to add
     */
    public void add(Task t) throws AmosDuplicateException {
        if (!isDuplicate(t)) {
            tasks.add(t);
        } else {
            throw new AmosDuplicateException();
        }
    }

    /**
     * Adds a task to the list without validating duplicate.
     *
     * @param tsk the task to add
     */
    public void findAdd(Task tsk) {
        tasks.add(tsk);
    }

    /**
     * Deletes a task from the list by index.
     *
     * @param index the index of the task to remove
     */
    public void delete(int index) {
        tasks.remove(index);
    }

    /**
     * Gets a task from the list by index.
     *
     * @param index the index of the task
     * @return the task at the given index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task with the similar description
     *
     * @return the found task with same description
     */
    public TaskList find(String des) {
        TaskList temp = new TaskList();
        for (Task task : this.tasks) {
            if (task.getDescription().contains(des)) {
                temp.findAdd(task);
            }
        }
        return temp;
    }

    /**
     * Checks whether the given task is a duplicate of any task in the list.
     *
     * <p>This method iterates through all tasks in the current TaskList and
     * delegates the duplicate check to the {@code isDuplicateOf} method of the
     * Task class. A task is considered a duplicate if it matches an existing
     * task according to its type-specific criteria:
     * <ul>
     *     <li>Todo: duplicate if description matches</li>
     *     <li>Deadline: duplicate if description and due date match</li>
     *     <li>Event: duplicate if description, start time, and end time match</li>
     * </ul>
     *
     * @param newTask the task to check for duplicates
     * @return {@code true} if a duplicate task exists in the list, {@code false} otherwise
     */
    public boolean isDuplicate(Task newTask) {
        for (Task existing : this.tasks) {
            if (newTask.isDuplicateOf(existing)) { // delegate to Task itself
                return true;
            }
        }
        return false;
    }

}
