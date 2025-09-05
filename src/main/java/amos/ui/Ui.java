package amos.ui;

import amos.tasks.*;

import java.util.Scanner;

/**
 * Handles user interaction in the Amos application.
 *
 * <p>This class is responsible for reading user input, displaying messages,
 * printing task lists, and showing errors or notifications to the user.</p>
 */
public class Ui {
    /** Separator line for console output */
    public static final String  LINE = "\t------------------------------------------------------------";

    /** Scanner for reading user input */
    public static Scanner scan = new Scanner(System.in);

    /**
     * Reads a line of input from the user.
     *
     * @return the input string
     */
    public String scan(){
        return scan.nextLine();
    }

    /**
     * Prints a separator line.
     */
    public void printLine(){
        System.out.println(LINE);
    }

    /**
     * Prints a greeting message when the app starts.
     */
    public void greet(){
        printLine();
        System.out.println("\t Hello! I'm Amos");
        System.out.println("\t What can I do for you?\n");
        printLine();
    }

    /**
     * Prints a goodbye message when the app ends.
     */
    public void bye(){
        //Handle bye bye
        System.out.println("\t Bye. Hope to see you again soon!\n");
        printLine();
    }

    /**
     * Prints all tasks in the task list.
     *
     * @param lst the task list to display
     */
    public void printList(TaskList lst){
        if(lst.size() <= 0) {
            System.out.println("\t Nothing in the list now.\n");
        } else {
            System.out.println("\t Here are the tasks in your list:");
            for(int i = 0; i < lst.size(); i++){
                int j = i+1;
                System.out.println("\t " + j + ". " + lst.get(i) );
            }
        }
        printLine();
    }

    /**
     * Prints an exception message.
     *
     * @param e the exception to display
     */
    public void printException(Exception e){
        System.out.printf("\t %s\n\n",e);
        printLine();
    }

    /**
     * Prints a generic error message with additional details.
     *
     * @param msg the error message
     */
    public void printError(String msg) {
        System.out.println("Sry, there might have error somewhere!");
        System.out.println("\t " + msg);
        printLine();
    }

    /**
     * Prints a message indicating a task has been marked as done.
     *
     * @param task the task that was marked
     */
    public void printTaskMarked(Task task) {
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.println("\t " + task + "\n");
        printLine();
    }

    /**
     * Prints a message indicating a task has been unmarked.
     *
     * @param task the task that was unmarked
     */
    public void printTaskUnmarked(Task task) {
        System.out.println("\t OK! I've unmarked this task as done:");
        System.out.println("\t " + task + "\n");
        printLine();
    }

    /**
     * Prints a message when a new task is added.
     *
     * @param task the task that was added
     * @param size the current size of the task list
     */
    public void printTaskAdded(Task task, int size) {
        System.out.println("\t Got it. I've added this task: ");
        System.out.println("\t\t" + task);
        System.out.println("\t Now you have " + size + " tasks in the list.\n");
        printLine();
    }

    /**
     * Prints a message when a task is deleted.
     *
     * @param task the task that was deleted
     * @param size the current size of the task list
     */

    public void printTaskDeleted(Task task, int size) {
        System.out.println("\t Noted. I've removed this task:");
        System.out.println("\t\t" + task);
        System.out.println("\t Now you have " + size + " tasks in the list.\n");
        printLine();
    }

    /**
     * Prints a message when a task description is empty.
     *
     * @param type the type of task (todo, event, deadline)
     */
    public void printEmptyDescription(String type) {
        System.out.println("\t OOPS!!! The description of a " + type + " cannot be empty.\n");
        printLine();
    }

    /**
     * Prints a message indicating an invalid date/time format.
     */
    public void printInvalidDateTimeFormat() {
        System.out.println("\t Please enter the start/end time in the format of <DD/MM/YYYY HH:MM>!\n");
        printLine();
    }

    /**
     * Prints a message when a task cannot be deleted because it does not exist.
     */
    public void printInvalidDelete() {
        System.out.println("\t No such task to be deleted.\n");
        printLine();
    }
}
