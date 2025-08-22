public class Deadline extends Task{
    protected  final String by;
    public Deadline(String des){
        super(des.split("/by")[0].trim());
        this.by = des.split("/by")[1].trim();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
