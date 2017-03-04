package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.dao.EmptyResultDataAccessException;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import static uk.ac.ncl.daniel.baranowski.data.access.TableNames.TERMS_AND_CONDITIONS;

@DataAccessObject
public class TermsAndConditionsDAO {
    private static final Logger LOGGER = Logger.getLogger(TermsAndConditionsDAO.class.getName());
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TermsAndConditionsDAO(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(String terms){
        Map<String, Object> args = new HashMap<>();
        args.put(ColumnNames.TERMS.toString(), terms);

        new SimpleJdbcInsert(jdbcTemplate)
                      .withTableName(TERMS_AND_CONDITIONS.toString())
                      .usingColumns(ColumnNames.TERMS.toString())
                      .usingGeneratedKeyColumns(ColumnNames._ID.toString())
                      .execute(args);
    }

    public String getTermsById(int id){
        final String sql = String.format("SELECT %s FROM %s t WHERE t._id=?", ColumnNames.TERMS.toString(), TERMS_AND_CONDITIONS);
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public Integer getLatestId(){
        final String sql = String.format("SELECT %s FROM %s ORDER BY %s DESC LIMIT 1", ColumnNames._ID.toString(), TERMS_AND_CONDITIONS, ColumnNames._ID.toString());
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.log(Level.FINE, "There are no terms and conditions available in the database");
            return null;
        }
    }

    public int createNewTermsAndConditions(String newTerms){
        final String sql = String.format("INSERT INTO %s VALUES (default,?)", TERMS_AND_CONDITIONS);
        return jdbcTemplate.update(sql, newTerms);
    }

    private enum ColumnNames {
        TERMS("termsAndConditions"),
        _ID("_id");

        private final String columnName;

        ColumnNames(String tableName){
            this.columnName = tableName;
        }

        @Override
        public String toString(){
            return columnName;
        }
    }
}
