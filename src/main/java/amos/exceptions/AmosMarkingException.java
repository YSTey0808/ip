package amos.exceptions;

public class AmosMarkingException extends AmosException {
    private final boolean mark;

    public AmosMarkingException(boolean mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        if (mark) {
            return String.format("%s \n\t Opps! This has already been marked",
                    super.toString()
            );
        } else {
            return String.format("%s \n\t Opps! This has already been unmarked",
                    super.toString()
            );
        }

    }
}
