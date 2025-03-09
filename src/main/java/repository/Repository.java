package repository;

import dto.AdressePersonerDTO;
import model.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    private JdbcTemplate jdbcTemplate;

    public Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Indsæt en adresse og returnér den med dens genererede ID
    public Adresse insertAdresse(String street, String city) {
        String sql = "INSERT INTO adresse (street, city) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, street);
            ps.setString(2, city);
            return ps;
        }, keyHolder);

        int adresseId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        if (adresseId != -1) {
            return new Adresse(adresseId, street, city);
        } else {
            throw new RuntimeException("Kunne ikke indsætte adresse");
        }
    }

    // Indsæt en person og knyt den til en adresse
    public Person insertPerson(String name, int adresseId) {
        String sql = "INSERT INTO person (name, adresse_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, adresseId);
            return ps;
        }, keyHolder);

        int personId = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        if (personId != -1) {
            return new Person(personId, name, adresseId);
        } else {
            throw new RuntimeException("Kunne ikke indsætte person");
        }
    }

    // Indsæt en person og en tilhørende adresse via batchUpdate i to trin
    public AdressePersonerDTO insertPersonWithAdresse(String personName, String street, String city) {
        // Opret adresse og hent det genererede ID
        Adresse adresse = insertAdresse(street, city);

        // Indsæt person knyttet til denne adresse
        Person person = insertPerson(personName, adresse.getId());

        // Returnér en DTO med adresse og en liste af personer (startende med én person)
        return new AdressePersonerDTO(adresse, List.of(person));
    }

    public List<AdressePersonerDTO> getAllAdressesWithPersons() {
        String adresseSql = "SELECT * FROM adresse";
        List<Adresse> adresser = jdbcTemplate.query(adresseSql, new AdresseRowMapper());

        List<AdressePersonerDTO> resultater = new ArrayList<>();

        for (Adresse adresse : adresser) {
            String personerSql = "SELECT * FROM person WHERE adresse_id = ?";
            List<Person> personer = jdbcTemplate.query(personerSql, new PersonRowMapperExample3(), adresse.getId());

            resultater.add(new AdressePersonerDTO(adresse, personer));
        }
        return resultater;
    }

    public AdressePersonerDTO getAdressePersoner(int adresseId) {
        String adresseSql = "SELECT * FROM adresse WHERE id = ?";
        Adresse adresse = jdbcTemplate.queryForObject(adresseSql, new AdresseRowMapper(), adresseId);

        String personerSql = "SELECT * FROM person WHERE adresse_id = ?";
        List<Person> personer = jdbcTemplate.query(personerSql, new PersonRowMapperExample3(), adresseId);

        return new AdressePersonerDTO(adresse, personer);
    }
}