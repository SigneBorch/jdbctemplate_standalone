package model;

public class Adresse {
    private int id;
    private String street;
    private String city;

    public Adresse(int id, String street, String city) {
        this.id = id;
        this.street = street;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Adresse{id=" + id + ", street='" + street + "', city='" + city + "'}";
    }
}
