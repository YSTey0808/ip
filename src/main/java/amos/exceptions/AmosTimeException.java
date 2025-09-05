package amos.exceptions;

public class AmosTimeException extends AmosException {
    @Override
    public String toString() {
        return String.format("%s \n\t End time must be after the start time!",
                super.toString()
        );
    }
}
