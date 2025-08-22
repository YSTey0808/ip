import java.util.List;
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
        }  else if (res.startsWith("delete ")){
            //Check list
            delete(res);
        } else {
            //add into list
            add(res);
        }
    }

    public static void bye() {
        //Handle bye bye
        System.out.println("\t Bye. Hope to see you again soon!");
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
            String des = res.substring(4).trim();
            if(des.isEmpty()){
                System.out.println("\t OOPS!!! The description of a todo cannot be empty.");
                System.out.println(LINE);
                echo();
                return;
            } else {
                task = new Todo(des);
            }
        } else if(res.startsWith("event ")){
            String des = res.substring(5).trim();
            if(des.isEmpty()){
                System.out.println("\t OOPS!!! The description of a event cannot be empty.");
                System.out.println(LINE);
                echo();
                return;
            } else {
                task = new Event(des);
            }

        } else if(res.startsWith("deadline ")){
            String des = res.substring(8).trim();
            if(des.isEmpty()){
                System.out.println("\t OOPS!!! The description of a deadline cannot be empty.");
                System.out.println(LINE);
                echo();
                return;
            } else {
                task = new Deadline(des);
            }
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

    public static void delete(String res) {
        int size = lst.size();
        String str = res.substring(6).trim();
        int value = Integer.parseInt(str);
        if(value > 0 && value <= size){
            Task tsk = lst.get(value - 1);
            System.out.println("\t Noted. I've removed this task: ");
            System.out.println("\t\t" + tsk);
            lst.remove(value - 1);
            System.out.println("\t Now you have " + lst.size() + " tasks in the list.");
            System.out.println(LINE);
            echo();
        } else {
            System.out.println("\t No such task to be deleted.");
            System.out.println(LINE);
            echo();
        }

    }

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("\t Hello! I'm Amos");
        System.out.println("\t What can I do for you?");
        System.out.println(LINE);
        echo();
    }
}
