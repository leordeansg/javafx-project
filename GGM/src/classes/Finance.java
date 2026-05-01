package classes;

public class Finance extends CarRecord {
    private int financeid;
    private int amount;

    public Finance(
        int financeid,
        int carid,
        int amount,
        String note,
        String loggedOn
    ) {
        super(carid, note, loggedOn);
        this.financeid = financeid;
        this.amount = amount;
    }

    public Finance(
        int carid,
        int amount,
        String note
    ) {
        super(carid, note);
        this.amount = amount;
    }

    public int getFinanceid() {
        return financeid;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void validate() { 
        validateCommon(); /* Extends CarRecord */
        if (amount <= 0)
            throw new IllegalArgumentException("Amount cannot be negative");
    }
}