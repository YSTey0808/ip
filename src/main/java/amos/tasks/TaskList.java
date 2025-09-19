package amos.tasks;

import java.util.ArrayList;
import java.util.List;

import amos.exceptions.AmosDuplicateException;
import amos.exceptions.AmosException;
import amos.ui.Ui;

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
        if(!isDuplicate(t)){
            tasks.add(t);
        } else {
            throw new AmosDuplicateException();
        }
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
    public TaskList find(String des) throws AmosDuplicateException {
        TaskList temp = new TaskList();
        for (Task task : this.tasks) {
            if (task.getDescription().contains(des)) {
                temp.add(task);
            }
        }
        return temp;
    }

    public boolean isDuplicate(Task newTask) {
        for (Task existing : this.tasks) {
            if (newTask.isDuplicateOf(existing)) { // delegate to Task itself
                return true;
            }
        }
        return false;
    }

}
