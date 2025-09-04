public class DukeUnknownCommandException extends DukeException{
    private String command;

    public DukeUnknownCommandException(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return String.format("%s \nI have never seen the command '%s' before! PLS try again.",
                super.toString(),
                this.command
        );
    }
}
