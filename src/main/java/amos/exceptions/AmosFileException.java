package amos.exceptions;

public class AmosFileException extends AmosException{
    @Override
    public String toString() {
        return String.format("%s \n\t There's some error in your input file.",
                super.toString()
        );
    }
}
