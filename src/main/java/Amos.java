import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Paths;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Amos {
    public static final String  LINE = "\t------------------------------------------------------------";
    public static final String FILEPATH = "./data";
    public static final String FILENAME = "./Amos.txt";
    public static final String DATAPATH = Paths.get(FILEPATH,FILENAME).toString();
    public static Scanner scan = new Scanner(System.in);
    public static List<Task> lst = new ArrayList<>();
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

    public static void run() {
        while(true){
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
            } catch (AmosException e){
                System.out.printf("\t %s\n\n",e);
                System.out.println(LINE);
            }
        }

    }

    public static void loadFile() {
        try {
            File f = new File(DATAPATH);
            Scanner sc = new Scanner(f);
            boolean dateChecker = true;
            boolean errorChecker = true;
            while(sc.hasNextLine()){
                String entry = sc.nextLine();
                try {
                    readFile(entry);
                } catch(DateTimeParseException e) {
                    if(dateChecker){
                        System.out.println("\t Make sure the start/end time in the format of <DD/MM/YY HH:MM>!\n");
                        System.out.println(LINE);
                        dateChecker = false;
                    }
                } catch(AmosException e){
                    if(errorChecker){
                        System.out.printf("\t %s\n\n",e);
                        System.out.println(LINE);
                    }
                }
            }
        } catch (FileNotFoundException e){
            Amos.createTxt();
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
            tsk = parseEvent(des);
            break;

        case "D":
            tsk = parseDeadline(des);
            break;

        default:
            throw new AmosUnknownCommandException(command);
        }
        if(marking.equals("1")){
            tsk.markAsDone();
        }
        lst.add(tsk);
    }

    private static LocalDateTime parseDateTime(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(input.trim(), formatter);
    }

    private static Event parseEvent(String des) throws AmosException {
        int fromIndex = des.indexOf("|From:");
        int toIndex = des.indexOf("|To:");
        if (fromIndex == -1 || toIndex == -1) {
            throw new AmosTaskException("event");
        }

        String description = des.substring(0, fromIndex).trim();
        LocalDateTime from = parseDateTime(des.substring(fromIndex + 6, toIndex));
        LocalDateTime to = parseDateTime(des.substring(toIndex + 4));

        if (from.isAfter(to)) {
            throw new AmosTimeException();
        }

        return new Event(description, from, to);
    }

    private static Deadline parseDeadline(String des) throws AmosException {
        int byIndex = des.indexOf("|By:");
        if (byIndex == -1) {
            throw new AmosTaskException("deadline");
        }

        String description = des.substring(0, byIndex).trim();
        LocalDateTime by = parseDateTime(des.substring(byIndex + 4));

        return new Deadline(description, by);
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
            try{
                Task task = parseDeadline(des);
                lst.add(task);
                System.out.println("\t Got it. I've added this task: ");
                System.out.println("\t\t" + task);
                System.out.println("\t Now you have " + lst.size() + " tasks in the list.\n");
                System.out.println(LINE);
            } catch (DateTimeParseException e){
                System.out.println("\t Please enter the start/end time in the format of <DD/MM/YYYY HH:MM>!\n");
                System.out.println(LINE);
            } catch (Exception e){
                throw new AmosTaskException("deadline");
            }

        }
    }

    private static void addEvent(String des) throws AmosException {
        if(des.isEmpty()){
            System.out.println("\t OOPS!!! The description of a event cannot be empty.\n");
            System.out.println(LINE);
        } else {
            try {
                Task task = parseEvent(des);
                lst.add(task);
                System.out.println("\t Got it. I've added this task: ");
                System.out.println("\t\t" + task);
                System.out.println("\t Now you have " + lst.size() + " tasks in the list.\n");
                System.out.println(LINE);
            } catch (DateTimeParseException e){
                System.out.println("\t Please enter the start/end time in the format of <DD/MM/YYYY HH:MM>!\n");
                System.out.println(LINE);
            } catch (AmosTimeException e){
                throw e;
            } catch (Exception e) {
                throw new AmosTaskException("event");
            }


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

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("\t Hello! I'm Amos");
        System.out.println("\t What can I do for you?\n");
        System.out.println(LINE);
        loadFile();
        run();
    }
}
