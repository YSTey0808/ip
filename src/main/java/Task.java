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

    public void markAsDone() throws AmosMarkingException {
        if(this.isDone){
            throw new AmosMarkingException(isDone);
        } else {
            this.isDone = true;

        }

    }

    public void unmarkAsDone() throws AmosMarkingException {
        if(this.isDone){
            this.isDone = false;
        } else {
            throw new AmosMarkingException(isDone);
        }
    }

    public String writeTxt(){
        return getStatusIcon()+" | "+this.description;
    }

    @Override
    public String toString() {
        return "["+getStatusIcon()+ "] " + this.description;
    }

}