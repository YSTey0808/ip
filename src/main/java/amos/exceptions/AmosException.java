package amos.exceptions;

/**
 * Represents the base exception class for all custom exceptions in Amos.
 * <p>
 * This class extends {@link Exception} and can be used as a parent for more
 * specific exception types within the Amos application.
 * </p>
 */
public class AmosException extends Exception {
    /**
     * Returns a string representation of this exception.
     *
     * @return A fixed error message indicating that an error occurred.
     */
    @Override
    public String toString() {
        return "Sry, there might have error somewhere!";
    }
}
