package dto;

import model.Adresse;
import model.Person;

import java.util.List;

public class AdressePersonerDTO {
    private Adresse adresse;
    private List<Person> personer;

    public AdressePersonerDTO(Adresse adresse, List<Person> personer) {
        this.adresse = adresse;
        this.personer = personer;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public List<Person> getPersoner() {
        return personer;
    }

    @Override
    public String toString() {
        return "Adresse: " + adresse + ", Personer: " + personer;
    }
}

