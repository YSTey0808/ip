public class Event extends Task {
    protected  final String from;
    protected  final String to;

    public Event(String des){
        super(des.split("/from")[0].trim());
        String[] parts = des.split("/from")[1].split("/to");
        this.from = parts[0].trim();
        this.to = parts[1].trim();
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

}
