import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeParseException;

public class Amos {
    public static final String FILEPATH = "./data";
    public static final String FILENAME = "./Amos.txt";
    public static final String PATH = Paths.get(FILEPATH,FILENAME).toString();

    private final Storage storage;
    private final TaskList lst;
    private final Ui ui;

    public Amos(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        lst = new TaskList(storage.loadFile());
    }

    public void run() {
        ui.greet();
        while(true){
            try{
                String res = ui.scan();
                String[]  res_arr = res.split(" ", 2);
                CommandType command = Parser.getCommand(res_arr[0]);
                ui.printLine();
                
                switch (command){
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

                    default:
                        throw new AmosEmptyException();
                }
            } catch (AmosException e){
               ui.printException(e);
            }
        }
    }

    public void bye() {
        try {
            storage.write(lst);
            ui.bye();
        } catch (IOException e){
            ui.printError("Error when writing the file!!!");
        }
    }

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

    public void unmarkAsDone(String value_str) throws AmosException {
        try {
            int value = Parser.parseIndex(value_str);
            Task task = lst.get(value - 1);
            task.unmarkAsDone();
            ui.printTaskUnmarked(task);
        } catch (NumberFormatException | IndexOutOfBoundsException e){
            throw new AmosUnfoundTaskException();
        }
    }

    private void addTodo(String des){
       try{
           Task task = Parser.parseTodo(des);
           lst.add(task);
           ui.printTaskAdded(task, lst.size());
       } catch (AmosTaskException e) {
           ui.printEmptyDescription("todo task");
       }

    }

    private void addDeadline(String des) throws AmosTaskException {
        try{
            Task task = Parser.parseDeadline(des);
            lst.add(task);
            ui.printTaskAdded(task, lst.size());
        } catch (DateTimeParseException e){
            ui.printInvalidDateTimeFormat();
        } catch (AmosTaskException e) {
            ui.printEmptyDescription("deadline task");
        }  catch (Exception e){
            throw new AmosTaskException("deadline");
        }
    }

    private void addEvent(String des) throws AmosException {
        try {
            Task task = Parser.parseEvent(des);
            lst.add(task);
            ui.printTaskAdded(task, lst.size());
        } catch (DateTimeParseException e){
            ui.printInvalidDateTimeFormat();
        } catch (AmosTimeException e){
            ui.printException(e);
        } catch (AmosTaskException e) {
            ui.printEmptyDescription("event");
        }catch (Exception e) {
            throw new AmosTaskException("event");
        }

    }

    public void delete(String des) {
        try{
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

    public static void main(String[] args) {
        new Amos(PATH).run();
    }
}
