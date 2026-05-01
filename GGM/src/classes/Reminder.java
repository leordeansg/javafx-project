package classes;

public class Reminder implements Validate {
    private int reminderid;
    private String name;
    private String description;
    private String dueDate;
    private String createdOn;

    public Reminder(
        int reminderid, String name, String description, String dueDate, String createdOn
    ) {
        this.reminderid = reminderid;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.createdOn = createdOn;
    }

    public Reminder(
        String name, String description, String dueDate
    ) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getReminderid() {
        return reminderid;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getCreatedOn() {
        return createdOn;
    }
    
    @Override
    public String toString() {
        return "NAME: " + name + " | DUE DATE: " + dueDate + " | DESCRIPTION: " + description;
    }

    @Override /* Polymorphism */
    public void validate() {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Reminder name is required");
        if (dueDate == null || dueDate.trim().isEmpty())
            throw new IllegalArgumentException("Due date is required");
        if (description == null || description.trim().isEmpty())
            throw new IllegalArgumentException("Description is required");
    }
}
