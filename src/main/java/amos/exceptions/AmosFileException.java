package amos.exceptions;

/**
 * Represents an exception that occurs when there is an error related to
 * the input file in the Amos application.
 * <p>
 * This exception extends {@link AmosException} and provides a specific
 * error message to indicate problems with file input or processing.
 * </p>
 */
public class AmosFileException extends AmosException {
    /**
     * Returns a string representation of this exception with a message
     * indicating that there was an error in the input file.
     *
     * @return A detailed error message describing the file-related issue.
     */
    @Override
    public String toString() {
        return String.format("%s \n\t There's some error in your input file.",
                super.toString()
        );
    }
}
