package repository;


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
}