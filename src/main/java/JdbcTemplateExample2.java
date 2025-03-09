import model.Person;
import model.PersonRowMapperExample2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTemplateExample2 {

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

        // Indsæt testdata (undgå duplikerede data med INSERT IGNORE)
        jdbcTemplate.update("INSERT IGNORE INTO person (id, name) VALUES (?, ?)", 1, "Jens");
        jdbcTemplate.update("INSERT IGNORE INTO person (id, name) VALUES (?, ?)", 2, "Alice");
        jdbcTemplate.update("INSERT IGNORE INTO person (id, name) VALUES (?, ?)", 3, "Pia");

        // Hent listen af personer ved hjælp af query() og RowMapper
        List<Person> personList = jdbcTemplate.query("SELECT * FROM person", new PersonRowMapperExample2());

        // Udskriv listen af personer
        //personList.forEach(System.out::println);
        System.out.println(personList);
    }
}

