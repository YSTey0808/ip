import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected final LocalDateTime from;
    protected final LocalDateTime to;
    protected final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public Event(String des, LocalDateTime from, LocalDateTime to) throws AmosException {
        super(des);
        this.from = from;
        this.to = to;
    }

    @Override
    public String writeTxt() {
        return "E |" + super.writeTxt() + " |From:"+from.format(FORMATTER) + " |To: " + to.format(FORMATTER) ;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(From: " + from.format(FORMATTER) + " |To: " + to.format(FORMATTER) + ")";
    }

}
