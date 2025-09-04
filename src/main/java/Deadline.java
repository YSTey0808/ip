public class Deadline extends Task{
    protected final String by;

    public Deadline(String des) throws AmosTaskException {
        super(parseName(des));
        this.by = parseBy(des);
    }

    public static String parseName(String des) throws AmosTaskException {
        int fromIndex = des.indexOf("|By:");
        if (fromIndex == -1) {
            throw new AmosTaskException("event");
        }
        return des.substring(0, fromIndex).trim();
    }

    public static String parseBy(String des) throws AmosTaskException {
        try{
            return des.split("\\|By:")[1].trim();
        } catch (Exception e){
            throw new AmosTaskException("deadline");
        }
    }

    @Override
    public String writeTxt() {
        return "D |" + super.writeTxt() + " |By:" + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (By: " + by + ")";
    }
}
