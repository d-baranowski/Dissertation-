package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.PaperVersion;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@DataAccessObject
public class PaperVersionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PaperVersionDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(PaperVersion paper) {
        //TODO BEYOND MVP NOSONAR
    }

    public PaperVersion read(int paperId, int versionNo) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.testPaperId = ? AND t.versionNumber = ?", TableNames.TEST_PAPER_VERSION);
        return mapPaperVersion(jdbcTemplate.queryForMap(sql, paperId, versionNo));
    }

    public List<PaperVersion> readAll() {
        final String sql = String.format("SELECT * FROM %s", TableNames.TEST_PAPER_VERSION);

        List<PaperVersion> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql)) {
            result.add(mapPaperVersion(row));
        }

        return result;
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.TEST_PAPER_VERSION);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<PaperVersion> geByPaperId(int objId) {
        final String sql = String.format("SELECT * FROM %s WHERE testPaperId = ?", TableNames.TEST_PAPER_VERSION);

        List<PaperVersion> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, objId)) {
            result.add(mapPaperVersion(row));
        }

        return result;
    }

    public List<PaperVersion> getByAuthorId(String objId) {
        final String sql = String.format("SELECT * FROM %s WHERE authorId = ?", TableNames.TEST_PAPER_VERSION);

        List<PaperVersion> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, objId)) {
            result.add(mapPaperVersion(row));
        }

        return result;
    }

    public List<Integer> getVersionNumbersById(int sectionId) {
        final String sql = String.format("SELECT t.versionNumber FROM %s t WHERE t.testPaperId = ?", TableNames.TEST_PAPER_VERSION);
        List<Integer> result = new ArrayList<>();

        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, sectionId)) {
            result.add((Integer) row.get("versionNumber"));
        }

        return result;
    }

    public int getLatestVersionNo(int objId) {
        final String sql = String.format("SELECT MAX(t.versionNumber) AS latest"
                + " FROM %s t WHERE t.testPaperId = ?", TableNames.TEST_PAPER_VERSION);

        return (int) jdbcTemplate.queryForMap(sql, objId).get("latest");
    }

    public String getInstructionsText(int id, int versionNo) {
        final String sql = String.format("SELECT instructionsText FROM %s t WHERE t.testPaperId = ? AND t.versionNumber = ?", TableNames.TEST_PAPER_VERSION);
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
}
