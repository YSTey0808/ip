package amos.tasks;

public class Todo extends Task{
    public Todo(String des){
        super(des);
    }

    @Override
    public String writeTxt() {
        return "T |" + super.writeTxt();
    }

    @Override
    public String toString() {
        return "[T]" +  super.toString();
    }
}
