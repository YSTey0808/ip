package amos.exceptions;

/**
 * Represents an exception that is thrown when an unknown or unsupported
 * command is encountered in the Amos application.
 * <p>
 * This exception extends {@link AmosException} and indicates that the
 * user has entered a command that the system does not recognize.
 * </p>
 */
public class AmosUnknownCommandException extends AmosException {
    /**
     * The unrecognized command entered by the user.
     */
    private final String command;

    /**
     * Creates a new {@code AmosUnknownCommandException}.
     *
     * @param command The unknown command string that caused this exception.
     */
    public AmosUnknownCommandException(String command) {
        this.command = command;
    }

    /**
     * Returns a string representation of this exception with a message
     * informing the user that the entered command is not recognized.
     *
     * @return A detailed error message describing the unknown command issue.
     */
    @Override
    public String toString() {
        return String.format("%s \n\t I have never seen the command '%s' before! PLS try again.",
                super.toString(),
                this.command
        );
    }
}
