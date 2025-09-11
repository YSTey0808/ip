package amos.exceptions;

/**
 * Represents an exception that occurs when there is an error related to
 * a specific task in the Amos application.
 * <p>
 * This exception extends {@link AmosException} and indicates that the
 * provided task input may be invalid or incorrectly formatted.
 * </p>
 */
public class AmosTaskException extends AmosException {
    /**
     * The name or type of the task that caused the exception.
     */
    private final String task;

    /**
     * Creates a new {@code AmosTaskException}.
     *
     * @param task The task name or type that triggered this exception.
     */
    public AmosTaskException(String task) {
        this.task = task;
    }

    /**
     * Returns a string representation of this exception with a message
     * prompting the user to recheck the provided task input.
     *
     * @return A detailed error message describing the task-related issue.
     */
    @Override
    public String toString() {
        return String.format("%s \n\t Please check your input for the %s again!",
                super.toString(),
                this.task
        );
    }
}
