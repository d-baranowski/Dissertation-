package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDayEntry;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import static uk.ac.ncl.daniel.baranowski.data.access.TableNames.TEST_DAY_ENTRY;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.CANDIDATE_ID;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.DAY_ID;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.FINAL_MARK;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.MARKERS_ID;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.PAPER_ID;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.PAPER_VERSION_NO;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.STATUS;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.TERMS_AND_CONDITIONS;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.TIME_ALLOWED;

@DataAccessObject
public class TestDayEntryDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestDayEntryDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(TestDayEntry obj) {
        jdbcTemplate.update(String.format("INSERT INTO %s (testDayId,testPaperVersionNo, testPaperId, candidateId, termsAndConditionsId, timeAllowed) VALUES (?,?,?,?,?,?)", TEST_DAY_ENTRY),
                obj.getTestDayId(), obj.getPaperVersionNo(), obj.getPaperId(), obj.getCandidateId(), obj.getTermsAndConditionsId(), obj.getTimeAllowed());
    }

    public TestDayEntry createAndGet(TestDayEntry obj) {
        Map<String, Object> args = new HashMap<>();
        args.put(PAPER_VERSION_NO.toString(), obj.getPaperVersionNo());
        args.put(PAPER_ID.toString(), obj.getPaperId());
        args.put(CANDIDATE_ID.toString(), obj.getCandidateId());
        args.put(DAY_ID.toString(), obj.getTestDayId());
        args.put(STATUS.toString(), obj.getStatus());
        args.put(TERMS_AND_CONDITIONS.toString(), obj.getTermsAndConditionsId());
        args.put(TIME_ALLOWED.toString(), obj.getTimeAllowed());

        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TEST_DAY_ENTRY.toString())
                .usingColumns(
                        PAPER_VERSION_NO.toString()
                        , PAPER_ID.toString()
                        , CANDIDATE_ID.toString()
                        , DAY_ID.toString()
                        , STATUS.toString()
                        , TERMS_AND_CONDITIONS.toString()
                        , TIME_ALLOWED.toString())
                .usingGeneratedKeyColumns("_id")
                .executeAndReturnKey(args);
        return read(key.intValue());
    }

    public TestDayEntry read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TEST_DAY_ENTRY);
        return mapTestDayEntry(jdbcTemplate.queryForMap(sql, objId));
    }

    public void updateStatus(String status, int id) {
        final String sql = String.format("UPDATE %s t SET t.%s=? WHERE t._id=?", TEST_DAY_ENTRY, STATUS);
        jdbcTemplate.update(sql, status, id);
    }

    public List<TestDayEntry> readAll() {
        final String sql = String.format("SELECT * FROM %s t", TEST_DAY_ENTRY);
        List<TestDayEntry> result = new ArrayList<>();

        jdbcTemplate.queryForList(sql).forEach(row -> result.add(mapTestDayEntry(row)));

        return result;
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TEST_DAY_ENTRY);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<TestDayEntry> getByCandidateId(int candidateId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.candidateId = ?", TEST_DAY_ENTRY);
        List<TestDayEntry> result = new ArrayList<>();

        jdbcTemplate.queryForList(sql, candidateId).forEach(row -> result.add(mapTestDayEntry(row)));

        return result;
    }

    public List<TestDayEntry> getByDateLocation(String date, String location) {
        final String sql = String.format(
                "SELECT t.candidateId, t._id, t.testDayId, t.testPaperVersionNo, t.testPaperId, t.timeAllowed, t.termsAndConditionsId " +
                        "FROM %s t, %s td " +
                        "WHERE td.date = ? AND td.location = ? AND td._id = t.testDayId",
                TEST_DAY_ENTRY, TableNames.TEST_DAY);

        List<TestDayEntry> result = new ArrayList<>();

        jdbcTemplate.queryForList(sql, date, location).forEach(row -> result.add(mapTestDayEntry(row)));

        return result;
    }

    public List<TestDayEntry> getByEntryStatus(String entryStatus) {
        final String sql = String.format(
                "SELECT * FROM %s t WHERE t.testDayEntryStatus = ?",
                TEST_DAY_ENTRY);

        List<TestDayEntry> result = new ArrayList<>();

        jdbcTemplate.queryForList(sql, entryStatus).forEach(row -> result.add(mapTestDayEntry(row)));

        return result;
    }

    public String getMarkersId(int objId) {
        final String sql = String.format("SELECT t.markingLock from %s t WHERE t._id = ?", TEST_DAY_ENTRY);
        return jdbcTemplate.queryForObject(sql, String.class, objId);
    }

    public void setMarkerId(int attemptId, String markersId) {
        final String sql = String.format("UPDATE %s t SET t.%s=? WHERE t._id=?", TEST_DAY_ENTRY, MARKERS_ID);
        jdbcTemplate.update(sql, markersId, attemptId);
    }

    public void updateFinalMark(int id, int mark) {
        final String sql = String.format("UPDATE %s t SET t.%s=? WHERE t._id=?", TEST_DAY_ENTRY,FINAL_MARK);
        jdbcTemplate.update(sql, mark, id);
    }

    enum ColumnNames {
        PAPER_VERSION_NO("testPaperVersionNo"),
        PAPER_ID("testPaperId"),
        DAY_ID("testDayId"),
        CANDIDATE_ID("candidateId"),
        STATUS("testDayEntryStatus"),
        MARKERS_ID("markingLock"),
        FINAL_MARK("finalMark"),
        TERMS_AND_CONDITIONS("termsAndConditionsId"),
        TIME_ALLOWED("timeAllowed");

        private final String columnName;

        ColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private TestDayEntry mapTestDayEntry(Map<String, Object> row) {
        return new TestDayEntry.Builder()
                .setId((int) row.get("_id"))
                .setCandidateId((int) row.get(CANDIDATE_ID.toString()))
                .setTestDayId((int) row.get(DAY_ID.toString()))
                .setPaperVersionNo((int) row.get(PAPER_VERSION_NO.toString()))
                .setPaperId((int) row.get(PAPER_ID.toString()))
                .setStatus((String) row.get(STATUS.toString()))
                .setFinalMark((Integer) row.get(FINAL_MARK.toString()))
                .setTermsAndConditions((Integer) row.get(TERMS_AND_CONDITIONS.toString()))
                .setTimeAllowed((Integer) row.get(TIME_ALLOWED.toString()))
                .build();
    }
}
