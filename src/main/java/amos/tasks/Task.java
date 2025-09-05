package amos.tasks;

import amos.exceptions.AmosMarkingException;

/**
 * Represents a task with a description and completion status.
 *
 * <p>This class provides methods to mark/unmark tasks, get status,
 * and format the task for display or file storage.</p>
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for display.
     *
     * @return "X" if done, " " if not done
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the status icon for writing to file.
     *
     * @return "1" if done, " " if not done
     */
    public String getTxtIcon() {
        return (isDone ? "1" : " ");
    }

    /**
     * Returns the task description.
     *
     * @return the description string
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Marks the task as done.
     *
     * @throws AmosMarkingException if the task is already marked
     */
    public void markAsDone() throws AmosMarkingException {
        if (this.isDone) {
            throw new AmosMarkingException(true);
        } else {
            this.isDone = true;
        }

    }

    /**
     * Unmarks the task as done.
     *
     * @throws AmosMarkingException if the task is already unmarked
     */
    public void unmarkAsDone() throws AmosMarkingException {
        if (this.isDone) {
            this.isDone = false;
        } else {
            throw new AmosMarkingException(false);
        }
    }


    /**
     * Formats the task as a line for writing to file.
     *
     * @return the task as a string for storage
     */
    public String writeTxt() {
        return getTxtIcon() + " | " + this.description;
    }

    /**
     * Returns a string representation of the task for display.
     *
     * @return the task as a display string
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }

}