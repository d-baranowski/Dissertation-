package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.PaperVersion;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.ncl.daniel.baranowski.data.access.PaperVersionDAO.ColumnNames.*;
import static uk.ac.ncl.daniel.baranowski.data.access.TableNames.TEST_PAPER_VERSION;

@DataAccessObject
public class PaperVersionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PaperVersionDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(PaperVersion obj) {
        Map<String, Object> args = new HashMap<>();
        args.put(AUTHOR_ID.toString(), obj.getAuthorId());
        args.put(VERSION_NUMBER.toString(), obj.getVersionNo());
        args.put(INSTRUCTIONS_TEXT.toString(),  obj.getInstructionsText());
        args.put(TEST_PAPER_ID.toString(), obj.getPaperId());

        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TEST_PAPER_VERSION.toString())
                .usingColumns(
                        AUTHOR_ID.toString()
                        , VERSION_NUMBER.toString()
                        , INSTRUCTIONS_TEXT.toString()
                        , TEST_PAPER_ID.toString())
                .execute(args);
    }

    public PaperVersion read(int paperId, int versionNo) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.testPaperId = ? AND t.versionNumber = ?", TEST_PAPER_VERSION);
        return mapPaperVersion(jdbcTemplate.queryForMap(sql, paperId, versionNo));
    }

    public List<PaperVersion> readAll() {
        final String sql = String.format("SELECT * FROM %s", TEST_PAPER_VERSION);

        List<PaperVersion> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql)) {
            result.add(mapPaperVersion(row));
        }

        return result;
    }

    public void update(PaperVersion obj) {
        final String sql =
                "UPDATE TestPaperVersion s" +
                        " SET " +
                        " s.authorId = ?," +
                        " s.instructionsText = ?" +
                        " WHERE s.testPaperId = ? AND s.versionNumber = ?;";

        jdbcTemplate.update(sql, obj.getAuthorId(), obj.getInstructionsText(), obj.getPaperId(), obj.getVersionNo());
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TEST_PAPER_VERSION);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<PaperVersion> geByPaperId(int objId) {
        final String sql = String.format("SELECT * FROM %s WHERE testPaperId = ?", TEST_PAPER_VERSION);

        List<PaperVersion> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, objId)) {
            result.add(mapPaperVersion(row));
        }

        return result;
    }

    public List<PaperVersion> getByAuthorId(String objId) {
        final String sql = String.format("SELECT * FROM %s WHERE authorId = ?", TEST_PAPER_VERSION);

        List<PaperVersion> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, objId)) {
            result.add(mapPaperVersion(row));
        }

        return result;
    }

    public List<Integer> getVersionNumbersById(int sectionId) {
        final String sql = String.format("SELECT t.versionNumber FROM %s t WHERE t.testPaperId = ?", TEST_PAPER_VERSION);
        List<Integer> result = new ArrayList<>();

        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, sectionId)) {
            result.add((Integer) row.get("versionNumber"));
        }

        return result;
    }

    public int getLatestVersionNo(int objId) {
        final String sql = String.format("SELECT MAX(t.versionNumber) AS latest"
                + " FROM %s t WHERE t.testPaperId = ?", TEST_PAPER_VERSION);

        return (int) jdbcTemplate.queryForMap(sql, objId).get("latest");
    }

    public String getInstructionsText(int id, int versionNo) {
        final String sql = String.format("SELECT instructionsText FROM %s t WHERE t.testPaperId = ? AND t.versionNumber = ?", TEST_PAPER_VERSION);
        return jdbcTemplate.queryForObject(sql, String.class, id, versionNo);
    }

    public String getAuthorId(int id, int versionNo) {
        final String sql = "SELECT authorId FROM TestPaperVersion WHERE testPaperId=? AND versionNumber=?";
        return jdbcTemplate.queryForObject(sql, String.class, id, versionNo);
    }

    private PaperVersion mapPaperVersion(Map<String, Object> row) {
        return new PaperVersion.Builder()
                .setAuthorId((String) row.get("authorId"))
                .setPaperId((int) row.get("testPaperId"))
                .setInstructionsText((String) row.get("instructionsText"))
                .setVersionNo((int) row.get("versionNumber"))
                .build();
    }

    public boolean checkIfVersionIsUsed(int id, int versionNo) {
        final String sql = "SELECT count(*) > 0 FROM Exam e WHERE e.testPaperId = ? AND e.testPaperVersionNo = ?";

        return jdbcTemplate.queryForObject(sql, Boolean.class, id, versionNo);
    }

    public enum ColumnNames {
        AUTHOR_ID("authorId"),
        TEST_PAPER_ID("testPaperId"),
        INSTRUCTIONS_TEXT("instructionsText"),
        VERSION_NUMBER("versionNumber");

        private final String columnName;

        ColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private String getFieldNames(String prepend) {
        StringBuilder builder = new StringBuilder();
        for (ColumnNames column : ColumnNames.values()) {
            builder.append(prepend);
            builder.append(column.columnName);
            if (!column.equals(ColumnNames.values()[ColumnNames.values().length - 1])) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

}
