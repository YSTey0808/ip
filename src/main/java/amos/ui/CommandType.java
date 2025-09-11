package amos.ui;

/**
 * Represents the different types of commands that can be issued
 * in the Amos application.
 * <p>
 * Each enum constant corresponds to a specific command
 * that the user can execute.
 * </p>
 */
public enum CommandType {
    /** Command to exit the application. */
    BYE,

    /** Command to list all tasks. */
    LIST,

    /** Command to unmark a task as not completed. */
    UNMARK,

    /** Command to create a todo task. */
    TODO,

    /** Command to create an event task. */
    EVENT,

    /** Command to create a deadline task. */
    DEADLINE,

    /** Command to mark a task as completed. */
    MARK,

    /** Command to delete a task. */
    DELETE,

    /** Command to find tasks by a search keyword. */
    FIND
}
