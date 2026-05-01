package classes;

public class Service extends CarRecord {
    private int serviceid;
    private int miles;

    public Service(int serviceid, int carid, int miles, String note, String loggedOn) {
        super(carid, note, loggedOn);
        this.serviceid = serviceid;
        this.miles = miles;
    }

    public Service(int carid, int miles, String note) {
        super(carid, note);
        this.miles = miles;
    }

    public int getServiceid() {
        return serviceid;
    }

    public int getMiles() {
        return miles;
    }

    @Override
    public void validate() {
        validateCommon(); /* Extends CarRecord */
        if (miles < 0)
            throw new IllegalArgumentException("Miles cannot be negative");
    }
}