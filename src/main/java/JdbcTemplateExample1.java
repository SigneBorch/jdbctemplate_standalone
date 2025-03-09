import model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplateExample1 {

    public static void main(String[] args) {
        // Opret DataSource manuelt
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/jdbctemplate_test", "root", "12345678");

        // Opret JdbcTemplate med DataSource
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Slet eksisterende tabeller, hvis de findes
        jdbcTemplate.execute("DROP TABLE IF EXISTS person");

        // Opret en tabel (hvis den ikke findes)
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS person (id INT PRIMARY KEY, name VARCHAR(100))");

        // Indsæt testdata (undgå duplikerede data ved at bruge INSERT IGNORE)
        jdbcTemplate.update("INSERT IGNORE INTO person (id, name) VALUES (?, ?)", 1, "Jens");
        jdbcTemplate.update("INSERT IGNORE INTO person (id, name) VALUES (?, ?)", 2, "Alice");
        jdbcTemplate.update("INSERT IGNORE INTO person (id, name) VALUES (?, ?)", 3, "Pia");

        // Hent Person-liste fra databasen ved hjælp af queryForRowSet()
        List<Person> personList = new ArrayList<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM person");

        while (rowSet.next()) {
            int id = rowSet.getInt("id");
            String name = rowSet.getString("name");
            personList.add(new Person(id, name));
        }

        // Udskriv listen af personer
        System.out.println(personList);
    }
}
