package amos.exceptions;

/**
 * Represents an exception that occurs when attempting to mark or unmark
 * a task that is already in the desired state.
 * <p>
 * This exception extends {@link AmosException} and indicates that the
 * marking action is redundant.
 * </p>
 */
public class AmosMarkingException extends AmosException {
    /** Whether the exception was caused by attempting to mark a task. */
    private final boolean mark;

    /**
     * Creates a new {@code AmosMarkingException}.
     *
     * @param mark {@code true} if the exception was caused by trying to mark
     *             an already marked task, {@code false} if it was caused by
     *             trying to unmark an already unmarked task.
     */
    public AmosMarkingException(boolean mark) {
        this.mark = mark;
    }

    /**
     * Returns a string representation of this exception with a message
     * indicating whether the error was caused by marking or unmarking
     * a task that was already in the same state.
     *
     * @return A detailed error message describing the marking error.
     */
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
