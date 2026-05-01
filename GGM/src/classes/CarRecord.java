package classes;

public abstract class CarRecord implements Validate {
    protected int carid;
    protected String note;
    protected String loggedOn;
    
    protected CarRecord(int carid, String note, String loggedOn) {
        this.carid = carid;
        this.note = note;
        this.loggedOn = loggedOn;
    }

    protected CarRecord(int carid, String note) {
        this.carid = carid;
        this.note = note;
    }

    public int getCarid() {
        return carid;
    }

    public String getNote() {
        return note;
    }

    public String getLoggedOn() {
        return loggedOn;
    }

    protected void validateCommon() { /* Reused by subclasses Finance and Service */
        if (carid <= 0)
            throw new IllegalArgumentException("Car is required");
        if (note == null || note.trim().isEmpty())
            throw new IllegalArgumentException("Note is required");
    }
}
