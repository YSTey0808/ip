import java.util.Scanner;

public class Ui {
    public static final String  LINE = "\t------------------------------------------------------------";
    public static Scanner scan = new Scanner(System.in);

    public String scan(){
        return scan.nextLine();
    }

    public void close(){
        scan.close();
    }

    public void printLine(){
        System.out.println(LINE);
    }

    public void greet(){
        printLine();
        System.out.println("\t Hello! I'm Amos");
        System.out.println("\t What can I do for you?\n");
        printLine();
    }

    public void bye(){
        //Handle bye bye
        System.out.println("\t Bye. Hope to see you again soon!\n");
        printLine();
    }

    public void printList(TaskList lst){
        if(lst.size() <= 0) {
            System.out.println("\t Nothing in the list now.\n");
        } else {
            System.out.println("\t Here are the tasks in your list:");
            for(int i = 0; i < lst.size(); i++){
                int j = i+1;
                System.out.println("\t " + j + ". " + lst.get(i) );
            }
        }
        printLine();
    }

    public void printException(Exception e){
        System.out.printf("\t %s\n\n",e);
        printLine();
    }

    public void printError(String msg) {
        System.out.println("Sry, there might have error somewhere!");
        System.out.println("\t " + msg);
        printLine();
    }

    public void printTaskMarked(Task task) {
        System.out.println("\t Nice! I've marked this task as done:");
        System.out.println("\t " + task + "\n");
        printLine();
    }

    public void printTaskUnmarked(Task task) {
        System.out.println("\t OK! I've unmarked this task as done:");
        System.out.println("\t " + task + "\n");
        printLine();
    }

    public void printTaskAdded(Task task, int size) {
        System.out.println("\t Got it. I've added this task: ");
        System.out.println("\t\t" + task);
        System.out.println("\t Now you have " + size + " tasks in the list.\n");
        printLine();
    }

    public void printTaskDeleted(Task task, int size) {
        System.out.println("\t Noted. I've removed this task:");
        System.out.println("\t\t" + task);
        System.out.println("\t Now you have " + size + " tasks in the list.\n");
        printLine();
    }

    public void printEmptyDescription(String type) {
        System.out.println("\t OOPS!!! The description of a " + type + " cannot be empty.\n");
        printLine();
    }

    public void printInvalidDateTimeFormat() {
        System.out.println("\t Please enter the start/end time in the format of <DD/MM/YYYY HH:MM>!\n");
        printLine();
    }

    public void printInvalidDelete() {
        System.out.println("\t No such task to be deleted.\n");
        printLine();
    }
}
