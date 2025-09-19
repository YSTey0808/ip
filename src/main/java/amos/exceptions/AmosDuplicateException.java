package amos.exceptions;

/**
 * Represents an exception that is thrown when an unknown or unsupported
 * command is encountered in the Amos application.
 * <p>
 * This exception extends {@link AmosException} and indicates that the
 * user has entered a command that the system does not recognize.
 * </p>
 */
public class AmosDuplicateException extends AmosException {

    /**
     * Returns a string representation of this exception with a message
     * informing the user that the entered command is not recognized.
     *
     * @return A detailed error message describing the unknown command issue.
     */
    @Override
    public String toString() {
        return String.format("%s \n\t Duplicate task added! PLS try again.",
                super.toString()
        );
    }
}
