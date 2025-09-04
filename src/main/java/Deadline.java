import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    protected final LocalDateTime by;
    protected final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Deadline(String des,LocalDateTime by) throws AmosTaskException {
        super(des);
        this.by = by;
    }

    @Override
    public String writeTxt() {
        return "D |" + super.writeTxt() + " |By:" + by.format(FORMATTER);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (By: " + by.format(FORMATTER) + ")";
    }
}
