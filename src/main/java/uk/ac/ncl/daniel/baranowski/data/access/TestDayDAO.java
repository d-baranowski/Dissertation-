package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;

import static uk.ac.ncl.daniel.baranowski.data.access.TestDayDAO.ColumnNames.*;

@DataAccessObject
public class TestDayDAO {
    private JdbcTemplate jdbcTemplate;

    
    @Autowired
    private TestDayDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(TestDay obj) {
        jdbcTemplate.update(String.format("INSERT INTO %s (date,location) VALUES (?, ?)", TableNames.TEST_DAY), obj.getDate(), obj.getLocation());
    }

    public TestDay read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.TEST_DAY);
        return mapTestDay(jdbcTemplate.queryForMap(sql, objId));
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.TEST_DAY);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public TestDay getOrCreate(TestDayModel model) {
        final String sqlCheck = String.format(
                "select count(*) from %s t WHERE t.date = ? AND t.startTime = ? AND t.endTime = ? AND t.location = ?",
                TableNames.TEST_DAY);

        final String sqlGet = String.format(
                "select * from %s t WHERE t.date = ? AND t.startTime = ? AND t.endTime = ? AND t.location = ?"
                , TableNames.TEST_DAY);

        long startTime = model.getStartTime().getMillisOfDay();
        long endTime = model.getEndTime().getMillisOfDay();

        final boolean exists = jdbcTemplate.queryForObject(sqlCheck, Integer.class, model.getDate() ,startTime,endTime,model.getLocation()) > 0;

        if (exists) {
            return mapTestDay(jdbcTemplate.queryForMap(sqlGet, model.getDate(),startTime,endTime,model.getLocation()));
        } else {
            return insertAndGet(model.getDate(),startTime,endTime,model.getLocation());
        }
    }

    public TestDay insertAndGet(String date, Long startTime, Long endTime, String location) {
        Map<String, String> args = new HashMap<>();
        args.put(DATE.toString(), date);
        args.put(LOCATION.toString(), location);
        args.put(START_TIME.toString(), startTime.toString());
        args.put(END_TIME.toString(), endTime.toString());

        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TableNames.TEST_DAY.toString())
                .usingColumns("date","startTime", "endTime" ,"location").usingGeneratedKeyColumns("_id")
                .executeAndReturnKey(args);

        return read(key.intValue());
    }

    enum ColumnNames {
        DATE("date"),
        LOCATION("location"),
        END_TIME("endTime"),
        START_TIME("startTime");

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
                .build();
    }
}
