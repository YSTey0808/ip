import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Amos {
    public static final String  LINE = "\t------------------------------------------------------------";
    public static final String FILEPATH = "./data";
    public static final String FILENAME = "./Amos.txt";
    public static final String DATAPATH = Paths.get(FILEPATH,FILENAME).toString();
    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<Task> lst = new ArrayList<>();
    private enum CommandType {
        BYE, LIST, UNMARK,
        TODO, EVENT, DEADLINE,
        MARK, DELETE,
    }

    private static CommandType getCommand(String cmd) throws AmosUnknownCommandException {
        try {
            return  CommandType.valueOf(cmd.toUpperCase());
        } catch (IllegalArgumentException  e){
            throw new AmosUnknownCommandException(cmd);
        }
    }

    public static void run() throws AmosException {
        try{
            String res = scan.nextLine();
            String[]  res_arr = res.split(" ", 2);


            CommandType command = getCommand(res_arr[0]);
            System.out.println(LINE);

            switch (command){
                case BYE:
                    bye();
                    return;

                case LIST:
                    list();
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
            //Repeat
            run();
        } catch (AmosException e){
            System.out.printf("\t %s\n\n",e);
            System.out.println(LINE);
            run();
        }

    }

    public static void loadFile() {
        try {
            File f = new File(DATAPATH);
            Scanner sc = new Scanner(f);
            while(sc.hasNextLine()){
                String entry = sc.nextLine();
                readFile(entry);
            }
        } catch (FileNotFoundException e){
            Amos.createTxt();
        } catch (AmosException e) {
            System.out.printf("\t %s\n\n",e);
            System.out.println(LINE);
        }

    }

    private static void readFile(String entry) throws AmosException{
            String[] input = entry.split("\\|", 3);
            if(input.length<3){
                throw new AmosFileException();
            }
            String command = input[0].trim();
            String marking = input[1].trim();
            String des = input[2].trim();
            Task tsk;
            switch(command) {
                case "T":
                    tsk = new Todo(des);
                    break;

                case "E":
                    tsk = new Event(des);
                    break;

                case "D":
                    tsk = new Deadline(des);
                    break;

                default:
                    throw new AmosUnknownCommandException(command);
            }

            if(marking.equals("1")){
                tsk.markAsDone();
            }

             lst.add(tsk);
    }

    public static void createTxt(){
        File db = new File(Amos.DATAPATH);
        File dir = new File(Amos.FILEPATH);
        dir.mkdir();
        try {
            db.createNewFile();
        } catch (IOException e){
            System.out.println("Sry, there might have error somewhere!");
            System.out.println("Error when creating database!!!");
        }
    }

    public static void write() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(DATAPATH));
        for(Task tsk: lst){
            bw.write(tsk.writeTxt());
            bw.newLine();
        }
        bw.close();
    }


    public static void bye() {
        try {
            write();
            //Handle bye bye
            System.out.println("\t Bye. Hope to see you again soon!\n");
            System.out.println(LINE);
        } catch (IOException e){
            System.out.println("Sry, there might have error somewhere!");
            System.out.println("Error when writing the file!!!");
        }
    }

    public static void list() {
        if(lst.isEmpty()) {
            System.out.println("\t Nothing in the list now.\n");
        } else {
            System.out.println("\t Here are the tasks in your list:");
            for(int i = 0; i < lst.size(); i++){
                int j = i+1;
                System.out.println("\t " + j + ". " + lst.get(i) );
            }
        }

        System.out.println(LINE);

    }

    public static void markAsDone(String value_str) throws AmosException {
        try {
            int value = Integer.parseInt(value_str.trim());
            lst.get(value - 1).markAsDone();
            System.out.println("\t Nice! I've marked this task as done:");
            System.out.println("\t " + lst.get(value - 1) + "\n");
            System.out.println(LINE);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new AmosUnfoundTaskException();
        }
    }

    public static void unmarkAsDone(String value_str) throws AmosException {
        try {
            int value = Integer.parseInt(value_str.trim());
            lst.get(value - 1).unmarkAsDone();
            System.out.println("\t Nice! I've unmarked this task as done:");
            System.out.println("\t " + lst.get(value - 1) + "\n");
            System.out.println(LINE);
        } catch (NumberFormatException | IndexOutOfBoundsException e){
            throw new AmosUnfoundTaskException();
        }
    }

    private static void addTodo(String des){
        if(des.isEmpty()){
            System.out.println("\t OOPS!!! The description of a todo cannot be empty.\n");
            System.out.println(LINE);
        } else {
            Task task = new Todo(des);
            lst.add(task);
            System.out.println("\t Got it. I've added this task: ");
            System.out.println("\t\t" + task);
            System.out.println("\t Now you have " + lst.size() + " tasks in the list.\n");
            System.out.println(LINE);
        }
    }

    private static void addDeadline(String des) throws AmosTaskException {
        if(des.isEmpty()){
            System.out.println("\t OOPS!!! The description of a deadline cannot be empty.\n");
            System.out.println(LINE);
        } else {
            Task task = new Deadline(des);
            lst.add(task);
            System.out.println("\t Got it. I've added this task: ");
            System.out.println("\t\t" + task);
            System.out.println("\t Now you have " + lst.size() + " tasks in the list.\n");
            System.out.println(LINE);
        }
    }

    private static void addEvent(String des) throws AmosTaskException {
        if(des.isEmpty()){
            System.out.println("\t OOPS!!! The description of a event cannot be empty.\n");
            System.out.println(LINE);
        } else {
            Task task = new Event(des);
            lst.add(task);
            System.out.println("\t Got it. I've added this task: ");
            System.out.println("\t\t" + task);
            System.out.println("\t Now you have " + lst.size() + " tasks in the list.\n");
            System.out.println(LINE);
        }
    }


    public static void delete(String des) {
        int value = Integer.parseInt(des);
        try{
            Task tsk = lst.get(value - 1);
            System.out.println("\t Noted. I've removed this task: ");
            System.out.println("\t\t" + tsk);
            lst.remove(value - 1);
            System.out.println("\t Now you have " + lst.size() + " tasks in the list.\n");
            System.out.println(LINE);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("\t No such task to be deleted.\n");
            System.out.println(LINE);
        }
    }

    public static void main(String[] args) throws AmosException {
        System.out.println(LINE);
        System.out.println("\t Hello! I'm Amos");
        System.out.println("\t What can I do for you?\n");
        System.out.println(LINE);
        loadFile();
        run();
    }
}
