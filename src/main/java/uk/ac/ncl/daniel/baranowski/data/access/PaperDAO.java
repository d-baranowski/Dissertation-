package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Paper;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.ncl.daniel.baranowski.data.access.PaperDAO.ColumnNames.*;

@DataAccessObject
public class PaperDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PaperDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int create(Paper paper) {
        Map<String, Object> args = new HashMap<>();

        args.put("referenceName", paper.getName());
        args.put("timeAllowed", paper.getTimeAllowed());


        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TableNames.TEST_PAPER.toString())
                .usingColumns(
                        REFERENCE_NAME.toString(),
                        TIME_ALLOWED.toString()
                )
                .usingGeneratedKeyColumns(ID.toString())
                .executeAndReturnKey(args);
        return key.intValue();
    }

    public Paper read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.TEST_PAPER);
        return mapPaper(jdbcTemplate.queryForMap(sql, objId));
    }

    public List<Paper> readAll() {
        final String sql = String.format("SELECT * FROM %s t", TableNames.TEST_PAPER);
        List<Paper> result = new ArrayList<>();

        jdbcTemplate.queryForList(sql).forEach(row -> result.add(mapPaper(row)));

        return result;
    }

    public void update(Paper paper) {
        //NOSONAR TODO BEYOND MVP
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.TEST_PAPER);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private Paper mapPaper(Map<String, Object> row) {
        return new Paper.Builder()
                .setId((Integer) row.get("_id"))
                .setName((String) row.get("referenceName"))
                .setTimeAllowed((Integer) row.get("timeAllowed"))
                .build();
    }

    public enum ColumnNames {
        ID("_id"),
        REFERENCE_NAME("referenceName"),
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
