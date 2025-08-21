import java.util.Objects;
import java.util.Scanner;

public class Amos {
    public static final String  LINE = "\t------------------------------------------------------------";
    public static Scanner scan = new Scanner(System.in);

    public static void echo() {
        String res = scan.nextLine();
        System.out.println(LINE);
        if(Objects.equals(res, "bye")){
            System.out.println("\tBye. Hope to see you again soon!");
            System.out.println(LINE);
        } else {
            System.out.println("\t" + res);
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
