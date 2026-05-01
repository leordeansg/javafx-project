package classes;

public class Car implements Validate {
    private int carid;
    private String nickname;
    private String make;
    private String model;
    private int carYear;
    private String plate;
    private int currentMiles;
    private String addedOn; /* when car got added */

    public Car( /* reading from database */
        int carid,
        String nickname,
        String make,
        String model,
        int carYear,
        String plate,
        int currentMiles,
        String addedOn
    ) {
        this.carid = carid;
        this.nickname = nickname;
        this.make = make;
        this.model = model;
        this.carYear = carYear;
        this.plate = plate;
        this.currentMiles = currentMiles;
        this.addedOn = addedOn;
        }

    public Car( /* inserting into database */
        String nickname,
        String make,
        String model,
        int carYear,
        String plate,
        int currentMiles
    ) {
        this.nickname = nickname;
        this.make = make;
        this.model = model;
        this.carYear = carYear;
        this.plate = plate;
        this.currentMiles = currentMiles;
    }

    public int getCarid() { /* getters */
        return carid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getCarYear() {
        return carYear;
    }

    public String getPlate() {
        return plate;
    }

    public int getCurrentMiles() {
        return currentMiles;
    }

    public String getAddedOn() {
        return addedOn;
    }

    @Override
    public void validate() {
        if (nickname == null || nickname.trim().isEmpty())
            throw new IllegalArgumentException("Nickname is required");
        if (make == null || make.trim().isEmpty())
            throw new IllegalArgumentException("Make is required");
        if (model == null || model.trim().isEmpty())
            throw new IllegalArgumentException("Model is required");
        if (carYear < 1800 || carYear > 2100)
            throw new IllegalArgumentException("Year is invalid");
        if (plate == null || plate.trim().isEmpty())
            throw new IllegalArgumentException("Plate is required");
        if (currentMiles < 0)
            throw new IllegalArgumentException("Miles cannot be negative");
    }
}