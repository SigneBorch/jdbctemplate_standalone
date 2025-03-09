import model.Adresse;
import dto.AdressePersonerDTO;
import model.Person;
import repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

public class JdbcTemplateExample3 {
    public static void main(String[] args) {
        // Opret DataSource og JdbcTemplate
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/jdbctemplate_test", "root", "12345678");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Opret repository
        Repository repository = new Repository(jdbcTemplate);

        // Slet eksisterende tabeller, hvis de findes
        jdbcTemplate.execute("DROP TABLE IF EXISTS person");
        jdbcTemplate.execute("DROP TABLE IF EXISTS adresse");

        // Opret tabeller igen
        jdbcTemplate.execute("CREATE TABLE adresse (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "street VARCHAR(255) NOT NULL, " +
                "city VARCHAR(100) NOT NULL, " +
                "UNIQUE (street, city))");

        jdbcTemplate.execute("CREATE TABLE person (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "adresse_id INT, " +
                "FOREIGN KEY (adresse_id) REFERENCES adresse(id))");

        // Indsæt en adresse
        Adresse adresse = repository.insertAdresse("Nørrebrogade 29", "København");

        // Indsæt to personer der bor på adressen
        Person person1 = repository.insertPerson("Alice", adresse.getId());
        Person person2 = repository.insertPerson("Jens", adresse.getId());

        System.out.println("Adresse oprettet: " + adresse);
        System.out.println("Person oprettet: " + person1);
        System.out.println("Person oprettet: " + person2);

        System.out.println("\nIndsæt en person der bor på en adresse");
        AdressePersonerDTO dto1 = repository.insertPersonWithAdresse("Pia", "Haraldsgade 7", "Odense");
        System.out.println(dto1);

        System.out.println("\nAlle personer og adresser");
        List<AdressePersonerDTO> adressePersoner = repository.getAllAdressesWithPersons();
        for (AdressePersonerDTO dto : adressePersoner) {
            System.out.println(dto);
        }

        System.out.println("\nAlle personer tilknyttet adresse 1");
        AdressePersonerDTO dto = repository.getAdressePersoner(1);
        System.out.println(dto);
    }
}

