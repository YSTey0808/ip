package amos.exceptions;

/**
 * Represents an exception thrown when an event or task has an invalid time range
 * in the Amos application.
 * <p>
 * This exception extends {@link AmosException} and indicates that the end time
 * of a task occurs before its start time.
 * </p>
 */
public class AmosTimeException extends AmosException {

    /**
     * Returns a string representation of this exception with a message
     * informing the user that the end time must be after the start time.
     *
     * @return A detailed error message about the invalid time range.
     */
    @Override
    public String toString() {
        return String.format("%s \n\t End time must be after the start time!",
                super.toString()
        );
    }
}
