package amos.storage;

import amos.exceptions.*;
import amos.tasks.*;
import amos.ui.Parser;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath){
        this.filePath = filePath;
    }

    public List<Task> loadFile() {
        ArrayList<Task> lst = new ArrayList<>();
        try {
            File f = new File(filePath);
            Scanner sc = new Scanner(f);
            boolean dateChecker = true;
            boolean errorChecker = true;
            while(sc.hasNextLine()){
                String entry = sc.nextLine();
                try {
                    lst.add(readFile(entry));
                } catch(DateTimeParseException e) {
                    if(dateChecker){
                        System.out.println("\t Make sure the start/end time in the format of <DD/MM/YY HH:MM>!\n");
//                        System.out.println(LINE);
                        dateChecker = false;
                    }
                } catch(AmosException e){
                    if(errorChecker){
                        System.out.printf("\t %s\n\n",e);
//                        System.out.println(LINE);
                        errorChecker = false;
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e){
            createTxt();
        }

        return lst;

    }
    public void createTxt(){
        File db = new File(filePath);
        File dir = new File(db.getParent());
        dir.mkdir();
        try {
            db.createNewFile();
        } catch (IOException e){
            System.out.println("Sry, there might have error somewhere!");
            System.out.println("Error when creating database!!!");
        }
    }

    private Task readFile(String entry) throws AmosException{
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
                tsk = Parser.parseEvent(des);
                break;

            case "D":
                tsk = Parser.parseDeadline(des);
                break;

            default:
                throw new AmosUnknownCommandException(command);
        }
        if(marking.equals("1")){
            tsk.markAsDone();
        }
        return tsk;
    }

    public void write(TaskList lst) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        for(int i = 0; i < lst.size(); i++){
            Task tsk = lst.get(i);
            bw.write(tsk.writeTxt());
            bw.newLine();
        }
        bw.close();
    }
}
