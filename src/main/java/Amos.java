import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class Amos {
    public static final String  LINE = "\t------------------------------------------------------------";
    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<Task> lst = new ArrayList<Task>();

    public static void echo() {
        //Scanner
        String res = scan.nextLine();
        System.out.println(LINE);

        if(Objects.equals(res, "bye")){
            //bye
            bye();
        } else if (Objects.equals(res, "")){
            //Handle empty input
            System.out.println("\tPlease say something!");
            System.out.println(LINE);
            echo();
        } else if (Objects.equals(res, "list")){
            //Check list
            list();
        } else  if (res.startsWith("mark ")){
            //Check list
            markAsDone(res);
            echo();
        } else if (res.startsWith("unmark ")){
            //Check list
            unmarkAsDone(res);
            echo();
        } else {
            //add into list
            add(res);
        }
    }

    public static void bye() {
        //Handle bye bye
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public static void list() {
        if(lst.isEmpty()) {
            System.out.println("\t Nothing in the list now.");
        } else {
            System.out.println("\t Here are the tasks in your list:");
            for(int i = 0; i < lst.size(); i++){
                int j = i+1;
                System.out.println("\t " + j + ". " + lst.get(i));
            }
        }
        System.out.println(LINE);
        echo();
    }

    public static void markAsDone(String res) {
        int value = Integer.parseInt(res.substring(5));
        if(value > 0 && value <= lst.size()){
            lst.get(value - 1).markAsDone();
        } else {
            System.out.println("\t Cannot find such task.");
            System.out.println(LINE);
            return ;
        }
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.println("\t " + lst.get(value - 1));
        System.out.println(LINE);

    }

    public static void unmarkAsDone(String res) {
        int value = Integer.parseInt(res.substring(7));
        if(value > 0 && value <= lst.size()){
            lst.get(value - 1).unmarkAsDone();
        } else {
            System.out.println("\t Cannot find such task.");
            System.out.println(LINE);
            return ;
        }
        System.out.println("\t OK, I've marked this task as not done yet:");
        System.out.println("\t " + lst.get(value - 1));
        System.out.println(LINE);
    }

    public static void add(String res) {
        Task task;
        if(res.startsWith("todo ")){
            task = new Todo(res.substring(5).trim());
        } else if(res.startsWith("event ")){
            task = new Event(res.substring(6).trim());

        } else if(res.startsWith("deadline ")){
            task = new Deadline(res.substring(9).trim());
        } else{
            System.out.println("\t OOPS!!! I'm sorry, but I don't know what that means :-(");
            System.out.println(LINE);
            echo();
            return;
        }
        lst.add(task);
        System.out.println("\t Got it. I've added this task: ");
        System.out.println("\t\t" + task);
        System.out.println("\t Now you have " + lst.size() + " tasks in the list.");
        System.out.println(LINE);
        echo();
    }

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("\tHello! I'm Amos");
        System.out.println("\tWhat can I do for you?");
        System.out.println(LINE);
        echo();
    }
}
