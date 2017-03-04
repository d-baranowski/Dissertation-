package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Candidate;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DataAccessObject
public class CandidateDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CandidateDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(Candidate obj) {
        jdbcTemplate.update(String.format("INSERT INTO %s (name,surname) VALUES (?, ?)", TableNames.CANDIDATE), obj.getName(), obj.getSurname());
    }

    public Candidate createAndGet(Candidate obj) {
        Map<String, String> args = new HashMap<>();
        args.put(ColumnNames.NAME.toString(), obj.getName());
        args.put(ColumnNames.SURNAME.toString(), obj.getSurname());

        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TableNames.CANDIDATE.toString())
                .usingColumns(
                        ColumnNames.NAME.toString(),
                        ColumnNames.SURNAME.toString())
                .usingGeneratedKeyColumns("_id")
                .executeAndReturnKey(args);

        return read(key.intValue());
    }

    public Candidate read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.CANDIDATE);
        return mapCandidate(jdbcTemplate.queryForMap(sql, objId));
    }

    public void update(Candidate obj) {
        final String sql = String.format("UPDATE %s SET name=?,surname=? WHERE _id=?;", TableNames.CANDIDATE);
        jdbcTemplate.update(sql, obj.getName(), obj.getSurname(), obj.getId());
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.CANDIDATE);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Candidate> readAll() {
        final List<Candidate> result = new ArrayList<>();
        final String sql = String.format("SELECT * FROM %s t", TableNames.CANDIDATE);
        jdbcTemplate.queryForList(sql).forEach(row -> result.add(mapCandidate(row)));
        return result;
    }

    public List<Candidate> readAllEnrolledToModule(int moduleId) {
        final List<Candidate> result = new ArrayList<>();
        final String sql = String.format("SELECT c.`_id`, c.name, c.surname, c.hasExtraTime FROM %s c LEFT JOIN %s cm ON c.`_id` = cm.candidateId WHERE cm.moduleId = ?", TableNames.CANDIDATE, TableNames.CANDIDATE_MODULE);
        jdbcTemplate.queryForList(sql, moduleId).forEach(row -> result.add(mapCandidate(row)));
        return result;
    }


    private enum ColumnNames {
        ID("_id"),
        NAME("name"),
        SURNAME("surname"),
        HAS_EXTRA_TIME("hasExtraTime");

        private final String columnName;

        ColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private Candidate mapCandidate(Map<String, Object> row) {
        return new Candidate.Builder()
                .setId((int) row.get("_id"))
                .setName((String) row.get("name"))
                .setSurname((String) row.get("surname"))
                .setHasExtraTime((boolean) row.get("hasExtraTime"))
                .build();
    }
}
