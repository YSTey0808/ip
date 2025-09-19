package amos.ui;

import java.util.Scanner;

import amos.tasks.Task;
import amos.tasks.TaskList;

/**
 * Handles user interaction in the Amos application.
 *
 * <p>This class is responsible for reading user input, displaying messages,
 * printing task lists, and showing errors or notifications to the user.</p>
 */
public class Ui {

    /**
     * Scanner for reading user input
     */
    private static final Scanner scan = new Scanner(System.in);

    /**
     * Reads a line of input from the user.
     *
     * @return the input string
     */
    public String scan() {
        return scan.nextLine();
    }

    public void close() {
        scan.close();
    }


    /**
     * Prints a greeting message when the app starts.
     */
    public void greet() {
        System.out.println("\t Hello! I'm Amos");
        System.out.println("\t What can I do for you?\n");
    }

    /**
     * Prints a goodbye message when the app ends.
     */
    public void bye() {
        //Handle bye bye
        System.out.println("\t Bye. Hope to see you again soon!\n");

    }

    /**
     * Prints all tasks in the task list.
     *
     * @param lst the task list to display
     */
    public void printList(TaskList lst) {
        if (lst.size() <= 0) {
            System.out.println("Nothing in the list now.\n");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < lst.size(); i++) {
                int j = i + 1;
                System.out.println(j + ". " + lst.get(i));
            }
        }

    }

    /**
     * Prints an exception message.
     *
     * @param e the exception to display
     */
    public void printException(Exception e) {
        System.out.printf("%s\n\n", e);

    }

    /**
     * Prints a generic error message with additional details.
     *
     * @param msg the error message
     */
    public void printError(String msg) {
        System.out.println("Sry, there might have error somewhere!");
        System.out.println(msg);
    }

    /**
     * Prints a message indicating a task has been marked as done.
     *
     * @param task the task that was marked
     */
    public void printTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task + "\n");
    }

    /**
     * Prints a message indicating a task has been unmarked.
     *
     * @param task the task that was unmarked
     */
    public void printTaskUnmarked(Task task) {
        System.out.println("OK! I've unmarked this task as done:");
        System.out.println(task + "\n");
    }

    /**
     * Prints a message when a new task is added.
     *
     * @param task the task that was added
     * @param size the current size of the task list
     */
    public void printTaskAdded(Task task, int size) {
        System.out.println("Got it. I've added this task: ");
        System.out.println("\t" + task);
        System.out.println("Now you have " + size + " tasks in the list.\n");
    }

    /**
     * Prints a message when a task is deleted.
     *
     * @param task the task that was deleted
     * @param size the current size of the task list
     */
    public void printTaskDeleted(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println("\t\t" + task);
        System.out.println("Now you have " + size + " tasks in the list.\n");
    }

    /**
     * Prints a message when a task description is empty.
     *
     * @param type the type of task (todo, event, deadline)
     */
    public void printEmptyDescription(String type) {
        System.out.println("OOPS!!! The description of a " + type + " cannot be empty.\n");
    }

    /**
     * Prints a message indicating an invalid date/time format.
     */
    public void printInvalidDateTimeFormat() {
        System.out.println("Please enter the start/end time in the format of <DD/MM/YYYY HH:MM>!\n");
    }

    /**
     * Prints a message when a task cannot be deleted because it does not exist.
     */
    public void printInvalidDelete() {
        System.out.println("No such task to be deleted.\n");
    }

    /**
     * Prints found tasks in the task list.
     *
     * @param lst the task list to display
     */
    public void printFindList(TaskList lst) {
        if (lst.size() <= 0) {
            System.out.println("Can't find such task! PLS try again!\n");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (int i = 0; i < lst.size(); i++) {
                int j = i + 1;
                System.out.println(j + ". " + lst.get(i));
            }
        }

    }
}
