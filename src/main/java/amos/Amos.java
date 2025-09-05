package amos;

import java.io.IOException;
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
     *
     * @param filePath the path to the data file to load and save tasks
     */
    public Amos(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
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
                String[] res_arr = res.split(" ", 2);
                CommandType command = Parser.getCommand(res_arr[0]);
                ui.printLine();

                switch (command) {
                case BYE:
                    bye();
                    return;

                case LIST:
                    ui.printList(lst);
                    break;

                case MARK:
                    markAsDone(res_arr[1]);
                    break;

                case UNMARK:
                    unmarkAsDone(res_arr[1]);
                    break;

                case DELETE:
                    delete(res_arr[1]);
                    break;

                case TODO:
                    addTodo(res_arr[1]);
                    break;

                case EVENT:
                    addEvent(res_arr[1]);
                    break;

                case DEADLINE:
                    addDeadline(res_arr[1]);
                    break;

                case FIND:
                    find(res_arr[1]);
                    break;

                default:
                    throw new AmosEmptyException();
                }
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
     * @param value_str the 1-based index of the task to mark as done
     * @throws AmosException if the task index is invalid or not found
     */
    public void markAsDone(String value_str) throws AmosException {
        try {
            int value = Parser.parseIndex(value_str);
            Task task = lst.get(value - 1);
            task.markAsDone();
            ui.printTaskMarked(task);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new AmosUnfoundTaskException();
        }
    }

    /**
     * Unmarks a task as not done based on its index.
     *
     * @param value_str the 1-based index of the task to unmark
     * @throws AmosException if the task index is invalid or not found
     */
    public void unmarkAsDone(String value_str) throws AmosException {
        try {
            int value = Parser.parseIndex(value_str);
            Task task = lst.get(value - 1);
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
     * The entry point of the application.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        new Amos(PATH).run();
    }
}
