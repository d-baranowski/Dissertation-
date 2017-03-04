package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;
import uk.ac.ncl.daniel.baranowski.data.exceptions.DateFormatException;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static uk.ac.ncl.daniel.baranowski.common.Constants.TIME_PATTERN;
import static uk.ac.ncl.daniel.baranowski.common.DateUtils.DATE_TIME_FORMATTER;
import static uk.ac.ncl.daniel.baranowski.common.DateUtils.validDateFormat;
import static uk.ac.ncl.daniel.baranowski.data.access.TestDayDAO.ColumnNames.*;

@DataAccessObject
public class TestDayDAO {
    private JdbcTemplate jdbcTemplate;

    
    @Autowired
    private TestDayDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(TestDay obj) throws DateFormatException {
        validDateFormat(obj.getDate());

        jdbcTemplate.update(String.format("INSERT INTO %s ("+getFieldNames("")+") VALUES (?,?,?,?,?)",
                TableNames.TEST_DAY),
                obj.getDate(),
                obj.getLocation(),
                obj.getStartTime(),
                obj.getEndTime(),
                obj.getEndTimeWithExtraTime());
    }

    public TestDay read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.TEST_DAY);
        return mapTestDay(jdbcTemplate.queryForMap(sql, objId));
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.TEST_DAY);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public TestDay getOrCreate(TestDayModel model) throws DateFormatException {
        final String sqlCheck = String.format(
                "select count(*) from %s t WHERE t.date = ? AND t.startTime = ? AND t.endTime = ? AND t.endTimeWithExtraTime = ? AND t.location = ?",
                TableNames.TEST_DAY);

        final String sqlGet = String.format(
                "select * from %s t WHERE t.date = ? AND t.startTime = ? AND t.endTime = ? AND t.endTimeWithExtraTime = ? AND t.location = ?"
                , TableNames.TEST_DAY);

        String startTime = model.getStartTime().toString(TIME_PATTERN);
        String endTime = model.getEndTime().toString(TIME_PATTERN);
        String endTimeWithExtra = model.getEndTimeWithExtraTime().toString(TIME_PATTERN);
        validDateFormat(model.getDate());

        final boolean exists = jdbcTemplate.queryForObject(sqlCheck, Integer.class, model.getDate() ,startTime,endTime,endTimeWithExtra,model.getLocation()) > 0;

        if (exists) {
            return mapTestDay(jdbcTemplate.queryForMap(sqlGet, model.getDate(),startTime,endTime,endTimeWithExtra,model.getLocation()));
        } else {
            return insertAndGet(model.getDate(),startTime,endTime,endTimeWithExtra,model.getLocation());
        }
    }

    public TestDay insertAndGet(String date, String startTime, String endTime, String endTimeWithExtra, String location) throws DateFormatException {
        Map<String, String> args = new HashMap<>();
        args.put(DATE.toString(), date);
        args.put(LOCATION.toString(), location);
        args.put(START_TIME.toString(), startTime);
        args.put(END_TIME.toString(), endTime);
        args.put(END_TIME_WITH_EXTRA.toString(),endTimeWithExtra);

        validDateFormat(date);
        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TableNames.TEST_DAY.toString())
                .usingColumns("date","startTime", "endTime" ,"location", END_TIME_WITH_EXTRA.toString()).usingGeneratedKeyColumns("_id")
                .executeAndReturnKey(args);

        return read(key.intValue());
    }

    enum ColumnNames {
        ID("_id"),
        DATE("date"),
        LOCATION("location"),
        END_TIME("endTime"),
        START_TIME("startTime"),
        END_TIME_WITH_EXTRA("endTimeWithExtraTime");

        private final String columnName;

        ColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private TestDay mapTestDay(Map<String, Object> row) {
        return new TestDay.Builder()
                .setId((int) row.get("_id"))
                .setDate((String) row.get(DATE.toString()))
                .setLocation((String) row.get(LOCATION.toString()))
                .setStartTime((String) row.get(START_TIME.toString()))
                .setEndLocalTime((String) row.get(END_TIME.toString()))
                .setEndTimeWithExtraTime((String) row.get(END_TIME_WITH_EXTRA.toString()))
                .build();
    }

    @SuppressWarnings("Duplicates")
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
