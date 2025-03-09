package model;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdresseRowMapper implements RowMapper<Adresse> {
    @Override
    public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Adresse(
                rs.getInt("id"),
                rs.getString("street"),
                rs.getString("city")
        );
    }
}
