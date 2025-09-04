public class AmosUnknownCommandException extends AmosException{
    private final String command;

    public AmosUnknownCommandException(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return String.format("%s \n\t I have never seen the command '%s' before! PLS try again.",
                super.toString(),
                this.command
        );
    }
}
