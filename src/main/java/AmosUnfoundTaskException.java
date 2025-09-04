public class AmosUnfoundTaskException extends AmosException{
    @Override
    public String toString() {
        return String.format("%s \n\t Cannot find such task! PLS check your input again.",
                super.toString()
        );
    }
}
