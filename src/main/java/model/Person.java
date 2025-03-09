package model;

public class Person {
    private int id;
    private String name;
    private int adresseId;

    public Person(int id, String name, int adresseId) {
        this.id = id;
        this.name = name;
        this.adresseId = adresseId;
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAdresseId() {
        return adresseId;
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "', adresseId=" + adresseId + "}";
    }
}