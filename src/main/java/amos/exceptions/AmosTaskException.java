package amos.exceptions;

public class AmosTaskException extends AmosException{
    private final String task;

    public AmosTaskException(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return String.format("%s \n\t Please check your input for the %s again!",
                super.toString(),
                this.task
        );
    }
}
