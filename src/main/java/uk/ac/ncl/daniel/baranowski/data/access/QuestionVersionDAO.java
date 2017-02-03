package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersion;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@DataAccessObject
public class QuestionVersionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionVersionDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(QuestionVersion obj) {
        //TODO BEYOND MVP NOSONAR
    }

    public QuestionVersion read(int questionId, int versionNo) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.questionId = ? AND t.versionNumber = ?", TableNames.QUESTION_VERSION);
        return mapQuestionVersion(jdbcTemplate.queryForMap(sql, questionId, versionNo));
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.QUESTION_VERSION);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Set<QuestionVersion> getVersionsByQuestionId(int questionId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.questionId = ?", TableNames.QUESTION_VERSION);

        Set<QuestionVersion> result = new HashSet<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, questionId)) {
            result.add(mapQuestionVersion(row));
        }

        return result;
    }

    public List<Integer> getVersionNumbersById(int questionId) {
        final String sql = String.format("SELECT t.versionNumber FROM %s t WHERE t.questionId = ?", TableNames.QUESTION_VERSION);
        List<Integer> result = new ArrayList<>();

        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, questionId)) {
            result.add((Integer) row.get("versionNumber"));
        }

        return result;
    }

    public int getLatestVersionNo(int questionId) {
        final String sql = String.format("SELECT MAX(t.versionNumber) AS latest"
                + " FROM %s t WHERE t.questionId = ?", TableNames.QUESTION_VERSION);

        return (int) jdbcTemplate.queryForMap(sql, questionId).get("latest");
    }

    public List<QuestionVersion> getBySection(int sectionId, int sectonVersionNo) {
        final String sql = String.format(
                "SELECT qv.questionId, qv.text, qv.correctAnswer, qv.markingGuide, qv.timeScale, qv.versionNumber " +
                        "FROM %s qv JOIN %s e " +
                        "WHERE e.testPaperSectionId = ? AND e.testPaperSectionVersionNo = ? " +
                        "AND e.questionId = qv.questionId AND e.questionVersionNumber = qv.versionNumber ",
                TableNames.QUESTION_VERSION, TableNames.QUESTION_VERSION_ENTRY);

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, sectionId, sectonVersionNo);
        List<QuestionVersion> result = new ArrayList<>();

        for (Map<String, Object> row : resultList) {
            result.add(mapQuestionVersion(row));
        }

        return result;
    }

    public Integer getQuestionReferenceNumber(int sectionId, int sectionVersionNo, int questionId, int questionVersionNo) {
        final String sql = String.format(
                "SELECT e.referenceNumber " +
                        "FROM %s e " +
                        "WHERE e.testPaperSectionVersionNo = ? "
                        + "AND e.testPaperSectionId = ? "
                        + "AND e.questionVersionNumber = ? "
                        + "AND e.questionId = ? ",
                TableNames.QUESTION_VERSION_ENTRY);

        return jdbcTemplate.queryForObject(sql, Integer.class, sectionVersionNo, sectionId, questionVersionNo, questionId);
    }

    public int getTimeScale(int questionId, int questionVersionNo) {
        final String sql = String.format(
                "SELECT e.timeScale " +
                        "FROM %s e " +
                        "WHERE e.versionNumber = ? " +
                        "AND e.questionId = ? ",
                TableNames.QUESTION_VERSION);

        return jdbcTemplate.queryForObject(sql, Integer.class, questionVersionNo, questionId);
    }

    private QuestionVersion mapQuestionVersion(Map<String, Object> row) {
        return new QuestionVersion.Builder()
                .setQuestionId((int) row.get("questionId"))
                .setText((String) row.get("text"))
                .setCorrectAnswer((String) row.get("correctAnswer"))
                .setMarkingGuide((String) row.get("markingGuide"))
                .setTimeScale((int) row.get("timeScale"))
                .setVersionNo((int) row.get("versionNumber"))
                .build();
    }
}
