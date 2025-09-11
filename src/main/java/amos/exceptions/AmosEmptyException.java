package amos.exceptions;

/**
 * Represents an exception that is thrown when a user input is empty.
 * <p>
 * This exception extends {@link AmosException} and provides a more specific
 * error message to indicate that no input was provided by the user.
 * </p>
 */
public class AmosEmptyException extends AmosException {
    /**
     * Returns a string representation of this exception with a message
     * indicating that the user attempted to provide empty input.
     *
     * @return A detailed error message for empty input.
     */
    @Override
    public String toString() {
        return String.format("%s \n\t OOPS!!! Pls enter something! Don't leave it blank! :-(",
                super.toString()
        );
    }
}
