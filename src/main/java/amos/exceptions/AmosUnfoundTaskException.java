package amos.exceptions;

/**
 * Represents an exception thrown when a specified task cannot be found
 * in the Amos application.
 * <p>
 * This exception extends {@link AmosException} and indicates that the
 * user attempted to access or modify a task that does not exist.
 * </p>
 */
public class AmosUnfoundTaskException extends AmosException {

    /**
     * Returns a string representation of this exception with a message
     * informing the user that the requested task could not be found.
     *
     * @return A detailed error message about the unfound task.
     */
    @Override
    public String toString() {
        return String.format("%s \n\t Cannot find such task! PLS check your input again.",
                super.toString()
        );
    }
}
