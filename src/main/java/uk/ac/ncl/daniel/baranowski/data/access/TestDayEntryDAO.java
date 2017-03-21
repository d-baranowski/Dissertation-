package uk.ac.ncl.daniel.baranowski.data.access;

import nl.jqno.equalsverifier.internal.lib.bytebuddy.utility.RandomString;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDayEntry;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.ncl.daniel.baranowski.common.DateUtils.DATE_TIME_FORMATTER;
import static uk.ac.ncl.daniel.baranowski.data.access.TableNames.TEST_DAY_ENTRY;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO.ColumnNames.*;

@DataAccessObject
public class TestDayEntryDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestDayEntryDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(TestDayEntry obj) {
        String login = RandomString.make(5);
        String password = RandomString.make(5);
        jdbcTemplate.update(
                String.format(
                        "INSERT INTO %s (" + getFieldNames("") +
                                ") VALUES (?,?,?,?)", TEST_DAY_ENTRY),
                obj.getCandidateId(),
                obj.getExamId(),
                login,
                password
        );
    }

    public TestDayEntry createAndGet(TestDayEntry obj) {
        Map<String, Object> args = new HashMap<>();
        args.put(CANDIDATE_ID.toString(), obj.getCandidateId());
        args.put(STATUS.toString(), obj.getStatus());
        args.put(EXAM_ID.toString(), obj.getExamId());
        args.put(LOGIN.toString(), RandomString.make(5));
        args.put(PASSWORD.toString(), RandomString.make(5));

        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TEST_DAY_ENTRY.toString())
                .usingColumns(
                        CANDIDATE_ID.toString()
                        , STATUS.toString()
                        , EXAM_ID.toString()
                        , PASSWORD.toString()
                        , LOGIN.toString())
                .usingGeneratedKeyColumns("_id")
                .executeAndReturnKey(args);
        return read(key.intValue());
    }

    public TestDayEntry read(int objId) {
        final String sql = String.format("SELECT " + getFieldNames("t.") + " FROM %s t WHERE t._id = ?", TEST_DAY_ENTRY);
        return mapTestDayEntry(jdbcTemplate.queryForMap(sql, objId));
    }

    public void updateStatus(String status, int id) {
        final String sql = String.format("UPDATE %s t SET t.%s=? WHERE t._id=?", TEST_DAY_ENTRY, STATUS);
        jdbcTemplate.update(sql, status, id);
    }

    public List<TestDayEntry> readAll() {
        final String sql = String.format("SELECT " + getFieldNames("t.") + " FROM %s t", TEST_DAY_ENTRY);
        List<TestDayEntry> result = new ArrayList<>();

        jdbcTemplate.queryForList(sql).forEach(row -> result.add(mapTestDayEntry(row)));

        return result;
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TEST_DAY_ENTRY);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<TestDayEntry> getByCandidateId(int candidateId) {
        final String sql = String.format("SELECT " + getFieldNames("t.") + " FROM %s t WHERE t.candidateId = ?", TEST_DAY_ENTRY);
        List<TestDayEntry> result = new ArrayList<>();

        jdbcTemplate.queryForList(sql, candidateId).forEach(row -> result.add(mapTestDayEntry(row)));

        return result;
    }

    public List<TestDayEntry> getByDateLocation(String date, String location) {
        final String sql = String.format(
                "SELECT " + getFieldNames("t.") +
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
        final String sql = String.format("UPDATE %s t SET t.%s=? WHERE t._id=?", TEST_DAY_ENTRY, FINAL_MARK);
        jdbcTemplate.update(sql, mark, id);
    }

    public List<TestDayEntry> getByExamId(int examId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t.examId = ?", TEST_DAY_ENTRY);
        List<TestDayEntry> result = new ArrayList<>();

        jdbcTemplate.queryForList(sql, examId).forEach(row -> result.add(mapTestDayEntry(row)));

        return result;
    }

    public int findAttemptIdByCredentials(int examId, String login, String password) {
        final String sql = "SELECT a._id FROM TestDayEntry a INNER JOIN Exam e ON e.`_id` = a.examId WHERE e.`_id` = ? AND a.login = ? AND a.password = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, examId, login, password);
    }

    public int getExamId(int attemptId) {
        final String sql = "SELECT a.examId FROM TestDayEntry a WHERE a._id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, attemptId);
    }

    public int getTimeRemaining(int testAttemptId) throws IllegalArgumentException {
        final String sql =
                "SELECT\n" +
                "  d.date,\n" +
                "  CASE c.hasExtraTime\n" +
                "  WHEN TRUE\n" +
                "    THEN d.endTime\n" +
                "  WHEN FALSE\n" +
                "    THEN d.endTimeWithExtraTime END AS endTime\n" +
                "FROM TestDayEntry a INNER JOIN Candidate c ON a.candidateId = c.`_id`\n" +
                "  INNER JOIN Exam e ON e.`_id` = a.examId\n" +
                "  INNER JOIN TestDay d ON e.testDayId = d.`_id`\n" +
                "WHERE a.`_id` = ?;";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, testAttemptId);

        LocalDate examDate = LocalDate.parse((String)result.get("date"), DATE_TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse((String) result.get("endTime"));
        LocalTime currentTime = new LocalTime(System.currentTimeMillis());
        LocalDate currentDate = new LocalDate(System.currentTimeMillis());

        if (currentTime.isAfter(endTime) || currentDate.isAfter(examDate)) {
            return 0;
        }
        int minutes  = Minutes.minutesBetween(currentTime, endTime).getMinutes();
        return minutes;
    }

    public enum ColumnNames {
        ID("_id"),
        CANDIDATE_ID("candidateId"),
        STATUS("testDayEntryStatus"),
        MARKERS_ID("markingLock"),
        FINAL_MARK("finalMark"),
        LOGIN("login"),
        PASSWORD("password"),
        EXAM_ID("examId");

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

    private TestDayEntry mapTestDayEntry(Map<String, Object> row) {
        return new TestDayEntry.Builder()
                .setId((int) row.get("_id"))
                .setMarkingLock((String) row.get(MARKERS_ID.toString()))
                .setCandidateId((int) row.get(CANDIDATE_ID.toString()))
                .setStatus((String) row.get(STATUS.toString()))
                .setFinalMark((Integer) row.get(FINAL_MARK.toString()))
                .setExamId((Integer) row.get(EXAM_ID.toString()))
                .setPassword((String) row.get(PASSWORD.toString()))
                .setLogin((String) row.get(LOGIN.toString()))
                .build();
    }
}
