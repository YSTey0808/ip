import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class Amos {
    public static final String  LINE = "\t------------------------------------------------------------";
    public static Scanner scan = new Scanner(System.in);
    public static ArrayList<String> lst = new ArrayList<String>();

    public static void echo() {
        String res = scan.nextLine();
        System.out.println(LINE);
        if(Objects.equals(res, "bye")){
            System.out.println("\tBye. Hope to see you again soon!");
            System.out.println(LINE);
        } else if (Objects.equals(res, "")){
            System.out.println("\tPlease say something!");
            System.out.println(LINE);
            echo();
        } else if (Objects.equals(res, "list")){
            if(lst.size() == 0) {
                System.out.println("\t Nothing in the list now.");
            } else {
                for(int i = 0; i < lst.size(); i++){
                    int j = i+1;
                    System.out.println("\t " + j + ". " + lst.get(i));
                }
            }

            System.out.println(LINE);
            echo();
        } else {
            lst.add(res);
            System.out.println("\t added: " + res);
            System.out.println(LINE);
            echo();
        }
    }
    public static void main(String[] args) {
        System.out.println(LINE);
        System.out.println("\tHello! I'm Amos");
        System.out.println("\tWhat can I do for you?");
        System.out.println(LINE);
        echo();
    }
}
