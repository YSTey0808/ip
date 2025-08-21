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
        } else  if (res.length() > 4 && Objects.equals(res.substring(0,5), "mark ")){
            //Check list
            markAsDone(res);
            echo();
        } else if (res.length() > 6 &&Objects.equals(res.substring(0,7), "unmark ")){
            //Check list
            unmarkAsDone(res);
            echo();
        } else {
            //add into list
            lst.add(new Task(res));
            System.out.println("\t added: " + res);
            System.out.println(LINE);
            echo();
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

    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("\tHello! I'm Amos");
        System.out.println("\tWhat can I do for you?");
        System.out.println(LINE);
        echo();
    }
}
