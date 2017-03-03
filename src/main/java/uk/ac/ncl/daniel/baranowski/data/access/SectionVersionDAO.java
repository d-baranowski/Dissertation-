package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.SectionVersion;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static uk.ac.ncl.daniel.baranowski.data.access.SectionVersionDAO.ColumnNames.*;


@DataAccessObject
public class SectionVersionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SectionVersionDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(SectionVersion obj) {
        Map<String, Object> args = new HashMap<>();
        args.put(ID.toString(), obj.getSectionId());
        args.put(VERSION_NO.toString(), obj.getVersionNo());
        args.put(NO_OF_QUESTIONS.toString(),  obj.getNoOfQuestionsToAnswer());
        args.put(DESCRIPTION.toString(), obj.getDescription());
        args.put(TIME_ALLOWED.toString(),  obj.getTimeScale());

        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TableNames.TEST_PAPER_SECTION_VERSION.toString())
                .usingColumns(
                        ID.toString()
                        , VERSION_NO.toString()
                        , NO_OF_QUESTIONS.toString()
                        , DESCRIPTION.toString()
                        , TIME_ALLOWED.toString())
                .execute(args);
    }

    public SectionVersion read(int sectionId, int versionNo) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.testPaperSectionId = ? AND t.versionNumber = ?", TableNames.TEST_PAPER_SECTION_VERSION);
        return mapSectionVersion(jdbcTemplate.queryForMap(sql, sectionId, versionNo));
    }

    public void update(SectionVersion s) {
        final String sql =
                "UPDATE TestPaperSectionVersion s" +
                        " SET s.noOfQuestionsToAnswer = ?," +
                        " s.sectionDescription = ?," +
                        " s.timeScale = ?" +
                        " WHERE s.testPaperSectionId = ? AND s.versionNumber = ?;";

        jdbcTemplate.update(sql, s.getNoOfQuestionsToAnswer(), s.getDescription(), s.getTimeScale(), s.getSectionId(), s.getVersionNo());
    }

    public List<SectionVersion> readAll() {
        final String sql = String.format("SELECT * FROM %s", TableNames.TEST_PAPER_SECTION_VERSION);

        List<SectionVersion> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql)) {
            result.add(mapSectionVersion(row));
        }

        return result;
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.TEST_PAPER_SECTION_VERSION);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<SectionVersion> getByPaperVersionSections(int paperId, int versionNo) {
        final String sql = String.format(
                "SELECT sv.testPaperSectionId, sv.versionNumber, sv.noOfQuestionsToAnswer, sv.sectionDescription, sv.timeScale " +
                        "FROM %s e, %s sv " +
                        "WHERE e.TESTPAPERID = ? AND e.TESTPAPERVERSIONNO = ? " +
                        "AND e.testPaperSectionVersionNo = sv.versionNumber " +
                        "AND e.testPaperSectionId = sv.testPaperSectionId ",
                TableNames.TEST_PAPER_SECTION_VERSION_ENTRY,
                TableNames.TEST_PAPER_SECTION_VERSION);
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, paperId, versionNo);
        List<SectionVersion> result = new ArrayList<>();

        for (Map<String, Object> row : resultList) {
            result.add(mapSectionVersion(row));
        }

        return result;
    }

    public Integer getSectionReferenceNumber(int sectionId, int sectionVersionNo, int paperId, int paperVersionNo) {
        final String sql = String.format(
                "SELECT e.referenceNumber " +
                        "FROM %s e " +
                        "WHERE e.testPaperSectionVersionNo = ? "
                        + "AND e.testPaperSectionId = ? "
                        + "AND e.testPaperVersionNo = ? "
                        + "AND e.testPaperId = ? ",
                TableNames.TEST_PAPER_SECTION_VERSION_ENTRY);

        return jdbcTemplate.queryForObject(sql, Integer.class, sectionVersionNo, sectionId, paperVersionNo, paperId);
    }

    public List<Integer> getVersionNumbersById(int sectionId) {
        final String sql = String.format("SELECT t.versionNumber FROM %s t WHERE t.testPaperSectionId = ?", TableNames.TEST_PAPER_SECTION_VERSION);
        List<Integer> result = new ArrayList<>();

        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, sectionId)) {
            result.add((Integer) row.get("versionNumber"));
        }

        return result;
    }

    public Integer getLatestVersionNo(int objId) {
        final String sql = String.format("SELECT MAX(t.versionNumber) AS latest"
                + " FROM %s t WHERE t.testPaperSectionId = ?", TableNames.TEST_PAPER_SECTION_VERSION);

        return (int) jdbcTemplate.queryForMap(sql, objId).get("latest");
    }

    public boolean checkIfVersionIsUsed(int id, int versionNumber) {
        final String sql = "SELECT count(*) > 0 FROM TestPaperSectionVersionEntry e WHERE e.testPaperSectionId = ? AND e.testPaperSectionVersionNo = ?;";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id, versionNumber);
    }

    private SectionVersion mapSectionVersion(Map<String, Object> row) {
        return new SectionVersion.Builder()
                .setDescription((String) row.get(DESCRIPTION.toString()))
                .setNoOfQuestionsToAnswer((int) row.get("noOfQuestionsToAnswer"))
                .setTimeScale((int) row.get("timeScale"))
                .setSectionId((int) row.get("testPaperSectionId"))
                .setVersionNo((int) row.get("versionNumber"))
                .build();
    }


    public enum EntryColumnNames {
        SECTION_ID("testPaperSectionId"),
        SECTION_VERSION("testPaperSectionVersionNo"),
        PAPER_VERISON("testPaperVersionNo"),
        PAPER_ID("testPaperId"),
        SECTION_NO("referenceNumber");

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
        ID("testPaperSectionId"),
        VERSION_NO("versionNumber"),
        NO_OF_QUESTIONS("noOfQuestionsToAnswer"),
        DESCRIPTION("sectionDescription"),
        TIME_ALLOWED("timeScale");

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
            if (!column.equals(QuestionDAO.ColumnNames.values()[QuestionDAO.ColumnNames.values().length - 1])) {
                builder.append(",");
            }
        }
        return builder.toString();
    }
}
