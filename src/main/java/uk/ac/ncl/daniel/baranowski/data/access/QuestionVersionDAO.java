package uk.ac.ncl.daniel.baranowski.data.access;

import ch.qos.logback.classic.db.names.TableName;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersionEntry;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.*;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


import static uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionDAO.ColumnNames.*;
import static uk.ac.ncl.daniel.baranowski.data.access.TableNames.QUESTION_VERSION;
import static uk.ac.ncl.daniel.baranowski.data.access.TableNames.QUESTION_VERSION_ENTRY;

@DataAccessObject
public class QuestionVersionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionVersionDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(QuestionVersion obj) {
        Map<String, Object> args = new HashMap<>();
        args.put(QUESTION_ID.toString(), obj.getQuestionId());
        args.put(VERSION_NUMBER.toString(), obj.getVersionNo());
        args.put(CORRECT_ANSWER.toString(),  obj.getCorrectAnswer());
        args.put(MARKING_GUIDE.toString(), obj.getMarkingGuide());
        args.put(TIME_SCALE.toString(),  obj.getTimeScale());
        args.put(TEXT.toString(),obj.getText());

        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(QUESTION_VERSION.toString())
                .usingColumns(
                        QUESTION_ID.toString()
                        , VERSION_NUMBER.toString()
                        , CORRECT_ANSWER.toString()
                        , MARKING_GUIDE.toString()
                        , TIME_SCALE.toString()
                        , TEXT.toString())
                .execute(args);
    }

    public QuestionVersion read(int questionId, int versionNo) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.questionId = ? AND t.versionNumber = ?", QUESTION_VERSION);
        return mapQuestionVersion(jdbcTemplate.queryForMap(sql, questionId, versionNo));
    }

    public void update(QuestionVersion q) {
        final String sql = "UPDATE QuestionVersion q " +
                "SET " +
                " q.correctAnswer = ?," +
                " q.markingGuide = ?," +
                " q.text = ?," +
                " q.timeScale = ?" +
                " WHERE q.questionId = ?" +
                " AND q.versionNumber = ?";

        jdbcTemplate.update(sql, q.getCorrectAnswer(), q.getMarkingGuide(), q.getText(), q.getTimeScale(), q.getQuestionId(), q.getVersionNo());
    }

    public void deleteEntry(int questionId, int questionVersion, int sectionId, int sectionVersion) {
        final String sql = String.format(
                "DELETE FROM %s WHERE %s = ? AND %s = ? AND %s = ? AND %s = ?",
                QUESTION_VERSION_ENTRY,
                EntryColumnNames.QUESTION_ID,
                EntryColumnNames.QUESTION_VERISON,
                EntryColumnNames.SECTION_ID,
                EntryColumnNames.SECTION_VERSION);

        jdbcTemplate.update(sql, questionId, questionVersion, sectionId, sectionVersion);
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", QUESTION_VERSION);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Set<QuestionVersion> getVersionsByQuestionId(int questionId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.questionId = ?", QUESTION_VERSION);

        Set<QuestionVersion> result = new HashSet<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, questionId)) {
            result.add(mapQuestionVersion(row));
        }

        return result;
    }

    public void copyEntries(int sectionId, int oldVersion, int newVersion) {
        final String sql =
                "INSERT INTO QuestionVersionEntry (testPaperSectionVersionNo, testPaperSectionId, questionVersionNumber, questionId, referenceNumber)\n" +
                "    SELECT "+newVersion+", testPaperSectionId, questionVersionNumber, questionId, referenceNumber\n" +
                "    FROM QuestionVersionEntry WHERE testPaperSectionVersionNo = ? AND testPaperSectionId = ? ";

        jdbcTemplate.update(sql, oldVersion, sectionId);
    }

    public List<Integer> getVersionNumbersById(int questionId) {
        final String sql = String.format("SELECT t.versionNumber FROM %s t WHERE t.questionId = ?", QUESTION_VERSION);
        List<Integer> result = new ArrayList<>();

        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, questionId)) {
            result.add((Integer) row.get("versionNumber"));
        }

        return result;
    }

    public int getLatestVersionNo(int questionId) {
        final String sql = String.format("SELECT MAX(t.versionNumber) AS latest"
                + " FROM %s t WHERE t.questionId = ?", QUESTION_VERSION);

        Map<String, Object> map = jdbcTemplate.queryForMap(sql, questionId);
        if (map.get("latest") != null) {
            return (int) map.get("latest");
        } else {
            return 0;
        }
    }

    public List<QuestionVersion> getBySection(int sectionId, int sectionVersionNo) {
        final String sql = String.format(
                "SELECT qv.questionId, qv.text, qv.correctAnswer, qv.markingGuide, qv.timeScale, qv.versionNumber " +
                        "FROM %s qv JOIN %s e " +
                        "WHERE e.testPaperSectionId = ? AND e.testPaperSectionVersionNo = ? " +
                        "AND e.questionId = qv.questionId AND e.questionVersionNumber = qv.versionNumber ",
                QUESTION_VERSION, QUESTION_VERSION_ENTRY);

        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, sectionId, sectionVersionNo);
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
                QUESTION_VERSION_ENTRY);

        return jdbcTemplate.queryForObject(sql, Integer.class, sectionVersionNo, sectionId, questionVersionNo, questionId);
    }

    public int getTimeScale(int questionId, int questionVersionNo) {
        final String sql = String.format(
                "SELECT e.timeScale " +
                        "FROM %s e " +
                        "WHERE e.versionNumber = ? " +
                        "AND e.questionId = ? ",
                QUESTION_VERSION);

        return jdbcTemplate.queryForObject(sql, Integer.class, questionVersionNo, questionId);
    }

    public boolean checkIfVersionIsUsed(int id, int versionNo) {
        final String sql = "SELECT count(*) > 0 FROM QuestionVersionEntry e WHERE e.questionId = ? AND e.questionVersionNumber = ?";

        return jdbcTemplate.queryForObject(sql, Boolean.class, id, versionNo);
    }

    public QuestionVersionEntry getEntry(int id, int version, int sectionId, int sectionVersion) {
        final String sql = String.format(
                "SELECT * FROM %s  t WHERE t.%s = ? AND t.%s = ? AND t.%s = ? AND t.%s = ?",
                QUESTION_VERSION_ENTRY,
                EntryColumnNames.QUESTION_ID,
                EntryColumnNames.QUESTION_VERISON,
                EntryColumnNames.SECTION_ID,
                EntryColumnNames.SECTION_VERSION);

        try {
            return mapQuestionVersionEntry(jdbcTemplate.queryForMap(sql,id, version, sectionId, sectionVersion));
        } catch (EmptyResultDataAccessException e) {
                return new QuestionVersionEntry();
        }
    }

    public boolean checkIfVersionIsInSection(int id, int version, int sectionId, int sectionVersion) {
        final String sql = String.format(
                "SELECT count(*) > 0 FROM %s  t WHERE t.%s = ? AND t.%s = ? AND t.%s = ? AND t.%s = ?",
                QUESTION_VERSION_ENTRY,
                EntryColumnNames.QUESTION_ID,
                EntryColumnNames.QUESTION_VERISON,
                EntryColumnNames.SECTION_ID,
                EntryColumnNames.SECTION_VERSION);

        return jdbcTemplate.queryForObject(sql, Boolean.class, id, version, sectionId, sectionVersion);
    }

    private QuestionVersionEntry mapQuestionVersionEntry(Map<String, Object> row) {
        return new QuestionVersionEntry()
                .setSectionId((int) row.get(EntryColumnNames.SECTION_ID.toString()))
                .setSectionVersionNo((int) row.get(EntryColumnNames.SECTION_VERSION.toString()))
                .setQuestionId((int) row.get(EntryColumnNames.QUESTION_ID.toString()))
                .setQuestionVersionNumber((int) row.get(EntryColumnNames.QUESTION_VERISON.toString()))
                .setReferenceNumber((int) row.get(EntryColumnNames.QUESTION_NO.toString()));

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

    public void moveQuestion(int questionId, int questionVersion, int sectionId, int sectionVersion, int newPosition) {
        final String sql = String.format(
                "UPDATE %s SET %s = ? WHERE %s = ? AND %s = ? AND %s = ? AND %s = ?",
                QUESTION_VERSION_ENTRY,
                EntryColumnNames.QUESTION_NO,
                EntryColumnNames.SECTION_ID,
                EntryColumnNames.SECTION_VERSION,
                EntryColumnNames.QUESTION_ID,
                EntryColumnNames.QUESTION_VERISON
        );
        jdbcTemplate.update(sql,
                newPosition,
                sectionId,
                sectionVersion,
                questionId,
                questionVersion
        );
    }

    public void addQuestionToSection(QuestionVersionEntry e) {
        Map<String, Object> args = new HashMap<>();
        args.put(EntryColumnNames.QUESTION_NO.toString(), e.getReferenceNumber());
        args.put(EntryColumnNames.QUESTION_ID.toString(), e.getQuestionId());
        args.put(EntryColumnNames.QUESTION_VERISON.toString(),  e.getQuestionVersionNumber());
        args.put(EntryColumnNames.SECTION_ID.toString(), e.getSectionId());
        args.put(EntryColumnNames.SECTION_VERSION.toString(), e.getSectionVersionNo());

        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(QUESTION_VERSION_ENTRY.toString())
                .usingColumns(
                        EntryColumnNames.QUESTION_NO.toString(),
                        EntryColumnNames.QUESTION_ID.toString(),
                        EntryColumnNames.QUESTION_VERISON.toString(),
                        EntryColumnNames.SECTION_ID.toString(),
                        EntryColumnNames.SECTION_VERSION.toString())
                .execute(args);
    }

    public int getLastQuestionNumber(int sectionId, int sectionVersion) {
        final String sql = String.format(
                "SELECT MAX(t.%s) FROM %s t WHERE t.%s = ? AND t.%s = ?",
                EntryColumnNames.QUESTION_NO,
                QUESTION_VERSION_ENTRY,
                EntryColumnNames.SECTION_ID,
                EntryColumnNames.SECTION_VERSION);

        return jdbcTemplate.queryForObject(sql, Integer.class, sectionId, sectionVersion);
    }

    public boolean checkIfReferenceNumberIsUsed(int refNo, int sectionId, int sectionVersion) {
        final String sql = "SELECT Count(*) > 0 FROM "
                + TableNames.QUESTION_VERSION_ENTRY
                + " t WHERE t." + EntryColumnNames.SECTION_ID + " = ? AND "
                + " t." + EntryColumnNames.SECTION_VERSION + " = ? AND"
                + " t." + EntryColumnNames.QUESTION_NO + " = ?";

        return jdbcTemplate.queryForObject(sql, Boolean.class, sectionId, sectionVersion, refNo);
    }

    public void moveQuestionByPosition(int from, int to, int sectionId, int sectionVersionNo) {
        final String sql = String.format(
                "UPDATE %s SET %s = ? WHERE %s = ? AND %s = ? AND %s = ?",
                QUESTION_VERSION_ENTRY,
                EntryColumnNames.QUESTION_NO,
                EntryColumnNames.SECTION_ID,
                EntryColumnNames.SECTION_VERSION,
                EntryColumnNames.QUESTION_NO
        );
        jdbcTemplate.update(sql,
                to,
                sectionId,
                sectionVersionNo,
                from
        );
    }

    public enum EntryColumnNames {
        SECTION_ID("testPaperSectionId"),
        SECTION_VERSION("testPaperSectionVersionNo"),
        QUESTION_VERISON("questionVersionNumber"),
        QUESTION_ID("questionId"),
        QUESTION_NO("referenceNumber");

        private final String columnName;

        EntryColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    public enum ColumnNames {
        QUESTION_ID("questionId"),
        VERSION_NUMBER("versionNumber"),
        TEXT("text"),
        CORRECT_ANSWER("correctAnswer"),
        MARKING_GUIDE("markingGuide"),
        TIME_SCALE("timeScale");

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
