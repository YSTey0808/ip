public class Event extends Task {
    protected  final String from;
    protected  final String to;

    public Event(String des) throws AmosTaskException {
        super(parseName(des));
        String[] date = parseDate(des);
        this.from = date[0].trim();
        this.to = date[1].trim();
    }

    public static String parseName(String des) throws AmosTaskException {
        int fromIndex = des.indexOf("|From:");
        if (fromIndex == -1) {
            throw new AmosTaskException("event");
        }
        return des.substring(0, fromIndex).trim();
    }

    public static String[] parseDate(String des) throws AmosTaskException {
        try {
            String[] parts = des.split("\\|From:", 2);
            String[] dateRange = parts[1].split("\\|To:", 2);

            String from = dateRange[0].trim();
            String to = dateRange[1].trim();

            return new String[]{from, to};
        } catch (Exception e) {
            throw new AmosTaskException("event");
        }
    }

    @Override
    public String writeTxt() {
        return "E |" + super.writeTxt() + " |From:"+from + " |To: " + to ;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(From: " + from + " |To: " + to + ")";
    }

}
