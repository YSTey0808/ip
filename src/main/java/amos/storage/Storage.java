package amos.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import amos.exceptions.AmosException;
import amos.exceptions.AmosFileException;
import amos.exceptions.AmosUnknownCommandException;
import amos.tasks.Task;
import amos.tasks.TaskList;
import amos.tasks.Todo;
import amos.ui.Parser;

/**
 * Handles loading and saving tasks from/to a file.
 *
 * <p>This class is responsible for reading tasks from a text file,
 * creating the file if it does not exist, parsing task data,
 * and writing the tasks back to the file.</p>
 */
public class Storage {
    private final String filePath;

    // Task type constants
    private static final String TODO_TYPE = "T";
    private static final String DEADLINE_TYPE = "D";
    private static final String EVENT_TYPE = "E";

    // Status constants
    private static final String MARK_DONE = "1";

    private boolean dateChecker = true;

    /**
     * Creates a Storage object for a given file path.
     *
     * @param filePath the path to the storage file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return a list of tasks loaded from the file
     */
    public List<Task> loadFile() {
        ArrayList<Task> lst = new ArrayList<>();
        File f = new File(filePath);
        try {
            Scanner sc = new Scanner(f);

            while (sc.hasNextLine()) {
                String entry = sc.nextLine();
                try {
                    lst.add(readFile(entry));
                } catch (DateTimeParseException e) {
                    dateChecker();
                } catch (AmosException e) {
                        System.out.printf("\t %s\n\n", e);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            createTxt();
        }
        return lst;

    }

    /**
    * Check whether error printed before
    */
    public void dateChecker(){
        if (dateChecker) {
            System.out.println("\t Make sure the start/end time in the format of <DD/MM/YY HH:MM>!\n");
            this.dateChecker = false;
        }
    }


    /**
     * Creates the storage file and its parent directory if they do not exist.
     */
    public void createTxt() {
        File db = new File(filePath);
        File dir = new File(db.getParent());
        dir.mkdir();
        try {
            db.createNewFile();
        } catch (IOException e) {
            System.out.println("Sry, there might have error somewhere!");
            System.out.println("Error when creating database!!!");
        }
    }

    /**
     * Reads a single task from a line in the file.
     *
     * @param entry the line representing a task
     * @return the parsed Task
     * @throws AmosException if the line is invalid or the task type is unknown
     */
    private Task readFile(String entry) throws AmosException {
        String[] input = entry.split("\\|", 3);
        if (input.length < 3) {
            throw new AmosFileException();
        }
        String command = input[0].trim();
        String marking = input[1].trim();
        String description = input[2].trim();
        Task tsk = switch (command) {
            case TODO_TYPE -> new Todo(description);
            case EVENT_TYPE -> Parser.parseEvent(description);
            case DEADLINE_TYPE -> Parser.parseDeadline(description);
            default -> throw new AmosUnknownCommandException(command);
        };

        if (marking.equals(MARK_DONE)) {
            tsk.markAsDone();
        }
        return tsk;
    }

    /**
     * Writes all tasks to the storage file.
     *
     * @param tasks the task list to write
     * @throws IOException if an error occurs during writing
     */
    public void write(TaskList tasks) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for (int i = 0; i < tasks.size(); i++) {
            Task tsk = tasks.get(i);
            bw.write(tsk.writeTxt());
            bw.newLine();
        }
        bw.close();
    }
}
