public class AmosEmptyException extends AmosException{
    @Override
    public String toString() {
        return String.format("%s \n\t OOPS!!! Pls enter something! Don't leave it blank! :-(",
                super.toString()
        );
    }
}
