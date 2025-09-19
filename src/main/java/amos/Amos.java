package amos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;

import amos.exceptions.AmosEmptyException;
import amos.exceptions.AmosException;
import amos.exceptions.AmosTaskException;
import amos.exceptions.AmosTimeException;
import amos.exceptions.AmosUnfoundTaskException;
import amos.storage.Storage;
import amos.tasks.Task;
import amos.tasks.TaskList;
import amos.ui.CommandType;
import amos.ui.Parser;
import amos.ui.Ui;

/**
 * The main class for the Amos task management application.
 *
 * <p>This class handles initializing the storage, task list, and user interface,
 * processing user commands in a loop, and delegating tasks to the appropriate
 * methods such as adding, marking, unmarking, or deleting tasks.</p>
 */
public class Amos {
    /**
     * Default folder path for the data file.
     */
    public static final String FILEPATH = "./data";

    /**
     * Default data file name.
     */
    public static final String FILENAME = "./Amos.txt";

    /**
     * Full path to the data file.
     */
    public static final String PATH = Paths.get(FILEPATH, FILENAME).toString();

    private final Storage storage;
    private final TaskList lst;
    private final Ui ui;

    /**
     * Constructs an instance of the Amos app with the specified file path.
     */
    public Amos() {
        ui = new Ui();
        storage = new Storage(PATH);
        lst = new TaskList(storage.loadFile());


    }

    /**
     * Starts the application, displaying a greeting and processing user commands
     * until the user types "Bye" to exit.
     */
    public void run() {
        ui.greet();
        while (true) {
            try {
                String res = ui.scan();
                String[] resArr = res.split(" ", 2);
                CommandType command = Parser.getCommand(resArr[0]);
                assert command != null : "Command should not be null";

                switch (command) {
                case BYE:
                    bye();
                    return;

                case LIST:
                    ui.printList(lst);
                    break;

                case MARK:
                    markAsDone(resArr[1]);
                    break;

                case UNMARK:
                    unmarkAsDone(resArr[1]);
                    break;

                case DELETE:
                    delete(resArr[1]);
                    break;

                case TODO:
                    addTodo(resArr[1]);
                    break;

                case EVENT:
                    addEvent(resArr[1]);
                    break;

                case DEADLINE:
                    addDeadline(resArr[1]);
                    break;

                case FIND:
                    find(resArr[1]);
                    break;

                default:
                    throw new AmosEmptyException();
                }
            } catch (IndexOutOfBoundsException e) {
                ui.printError("Please check your input for the task again!");
            } catch (AmosException e) {
                ui.printException(e);
            }
        }
    }

    /**
     * Saves all tasks to storage and displays a goodbye message.
     */
    public void bye() {
        try {
            storage.write(lst);
            ui.bye();
        } catch (IOException e) {
            ui.printError("Error when writing the file!!!");
        }
    }

    /**
     * Marks a task as done based on its index.
     *
     * @param valueStr the 1-based index of the task to mark as done
     * @throws AmosException if the task index is invalid or not found
     */
    public void markAsDone(String valueStr) throws AmosException {
        try {
            int value = Parser.parseIndex(valueStr);
            Task task = lst.get(value - 1);
            assert task != null : "Task at index " + (value - 1) + " should not be null";
            task.markAsDone();
            ui.printTaskMarked(task);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new AmosUnfoundTaskException();
        }
    }

    /**
     * Unmarks a task as not done based on its index.
     *
     * @param valueStr the 1-based index of the task to unmark
     * @throws AmosException if the task index is invalid or not found
     */
    public void unmarkAsDone(String valueStr) throws AmosException {
        try {
            int value = Parser.parseIndex(valueStr);
            Task task = lst.get(value - 1);
            assert task != null : "Task at index " + (value - 1) + " should not be null";
            task.unmarkAsDone();
            ui.printTaskUnmarked(task);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new AmosUnfoundTaskException();
        }
    }

    /**
     * Adds a new Todo task with the given description.
     *
     * @param des the description of the Todo task
     */
    private void addTodo(String des) {
        try {
            Task task = Parser.parseTodo(des);
            lst.add(task);
            ui.printTaskAdded(task, lst.size());
        } catch (AmosTaskException e) {
            ui.printEmptyDescription("todo task");
        }
    }

    /**
     * Adds a new Deadline task with the given description and due date.
     *
     * @param des the description and due date in the format "description|By:dd/MM/yyyy HH:mm"
     * @throws AmosTaskException if the input is invalid or cannot be parsed
     */
    private void addDeadline(String des) throws AmosTaskException {
        try {
            Task task = Parser.parseDeadline(des);
            lst.add(task);
            ui.printTaskAdded(task, lst.size());
        } catch (DateTimeParseException e) {
            ui.printInvalidDateTimeFormat();
        } catch (AmosTaskException e) {
            ui.printEmptyDescription("deadline task");
        } catch (Exception e) {
            throw new AmosTaskException("deadline");
        }
    }

    /**
     * Adds a new Event task with the given description, start time, and end time.
     *
     * @param des the description and time in the format "description|From:dd/MM/yyyy HH:mm|To:dd/MM/yyyy HH:mm"
     * @throws AmosException if the input is invalid, times are inconsistent, or parsing fails
     */
    private void addEvent(String des) throws AmosException {
        try {
            Task task = Parser.parseEvent(des);
            lst.add(task);
            ui.printTaskAdded(task, lst.size());
        } catch (DateTimeParseException e) {
            ui.printInvalidDateTimeFormat();
        } catch (AmosTimeException e) {
            ui.printException(e);
        } catch (AmosTaskException e) {
            ui.printEmptyDescription("event");
        } catch (Exception e) {
            throw new AmosTaskException("event");
        }

    }

    /**
     * Deletes a task based on its index.
     *
     * @param des the 1-based index of the task to delete
     */
    public void delete(String des) {
        try {
            int value = Parser.parseIndex(des);
            Task tsk = lst.get(value - 1);
            assert tsk != null : "Task at index " + (value - 1) + " should not be null";
            lst.delete(value - 1);
            ui.printTaskDeleted(tsk, lst.size());
        } catch (IndexOutOfBoundsException e) {
            ui.printInvalidDelete();
        } catch (AmosUnfoundTaskException e) {
            ui.printException(e);
        }
    }

    /**
     * Find a task based on its description.
     *
     * @param des the description of task want to delete
     */
    public void find(String des) {
        TaskList temp = lst.find(des);
        ui.printFindList(temp);
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input the input from user
     */
    public String getResponse(String input) {
        // Save the current System.out
        PrintStream originalOut = System.out;

        // Create a buffer to capture output
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream printStreams = new PrintStream(stream);
        System.setOut(printStreams);

        try {
            // Process the input like in run(), but only once
            String[] resArr = input.split(" ", 2);
            CommandType command = Parser.getCommand(resArr[0]);

            switch (command) {
            case BYE:
                bye();
                break;
            case LIST:
                ui.printList(lst);
                break;
            case MARK:
                markAsDone(resArr[1]);
                break;
            case UNMARK:
                unmarkAsDone(resArr[1]);
                break;
            case DELETE:
                delete(resArr[1]);
                break;
            case TODO:
                addTodo(resArr[1]);
                break;
            case EVENT:
                addEvent(resArr[1]);
                break;
            case DEADLINE:
                addDeadline(resArr[1]);
                break;
            case FIND:
                find(resArr[1]);
                break;
            default:
                throw new AmosEmptyException();
            }

        } catch (IndexOutOfBoundsException e) {
            ui.printError("Please check your input for the task again!");
        } catch (AmosException e) {
            ui.printException(e);
        } catch (Exception e) {
            ui.printError("Unexpected error: " + e.getMessage());
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }

        // Return whatever was printed
        String output = stream.toString();
        assert output != null : "Response string should not be null";
        return output;

    }

    /**
     * The entry point of the application.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        new Amos().run();
    }
}
