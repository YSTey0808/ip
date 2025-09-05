package amos.tasks;

import amos.exceptions.AmosMarkingException;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getTxtIcon(){
        return (isDone ? "1" : " ");
    }

    public void markAsDone() throws AmosMarkingException {
        if(this.isDone){
            throw new AmosMarkingException(true);
        } else {
            this.isDone = true;
        }

    }

    public void unmarkAsDone() throws AmosMarkingException {
        if(this.isDone){
            this.isDone = false;
        } else {
            throw new AmosMarkingException(false);
        }
    }

    public String writeTxt(){
        return getTxtIcon()+" | "+this.description;
    }

    @Override
    public String toString() {
        return "["+getStatusIcon()+ "] " + this.description;
    }

}