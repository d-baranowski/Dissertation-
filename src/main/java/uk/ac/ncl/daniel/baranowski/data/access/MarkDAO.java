package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Mark;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import static uk.ac.ncl.daniel.baranowski.data.access.MarkDAO.ColumnNames.ACTUAL_MARK;
import static uk.ac.ncl.daniel.baranowski.data.access.MarkDAO.ColumnNames.COMMENT;
import static uk.ac.ncl.daniel.baranowski.data.access.MarkDAO.ColumnNames.MARKER_ID;

@DataAccessObject
public class MarkDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MarkDAO(JdbcTemplate jdbcTemplate) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = jdbcTemplate;
    }

    public int submitMark(Mark obj) {
        if (!exists(obj)) {
            return create(obj);
        } else {
           return update(obj);
        }
    }

    public Mark read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.MARK);
        return mapMark(jdbcTemplate.queryForMap(sql, objId));
    }

    public int getMarkSumForTestDayEntry(int testDayEntryId) {
        final String sql = String.format("SELECT SUM(%s) FROM %s WHERE _id IN (SELECT markId From %s where testDayEntryId =  ?)", ACTUAL_MARK, TableNames.MARK, TableNames.ANSWER);
        return jdbcTemplate.queryForObject(sql, Integer.class, testDayEntryId);
    }

    enum ColumnNames {
        MARKER_ID("markerId"),
        COMMENT("comment"),
        ACTUAL_MARK("actualMark");

        private final String columnName;

        ColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private boolean exists(Mark mark) {
        final String sql = String.format(
                "SELECT count(*) FROM %s t WHERE t._id = ? ", TableNames.MARK);

        if (mark.getId() <= 0) {
            return false;
        }
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, mark.getId());
        return count > 0;
    }

    private int create(Mark obj) {
        Map<String, Object> args = new HashMap<>();
        args.put(MARKER_ID.toString(), obj.getMarkerId());
        args.put(COMMENT.toString(), obj.getComment());
        args.put(ACTUAL_MARK.toString(), obj.getActualMark());

        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TableNames.MARK.toString())
                .usingColumns(
                        ColumnNames.MARKER_ID.toString()
                        , ColumnNames.COMMENT.toString()
                        , ColumnNames.ACTUAL_MARK.toString()
                )
                .usingGeneratedKeyColumns("_id")
                .executeAndReturnKey(args);
        return key.intValue();
    }

    private int update(Mark obj) {
        final String sql = String.format("UPDATE %s SET %s = ?, %s =? , %s = ? WHERE _id=?", TableNames.MARK ,MARKER_ID, COMMENT,ACTUAL_MARK);
        jdbcTemplate.update(sql, obj.getMarkerId(), obj.getComment(), obj.getActualMark(), obj.getId());
        return obj.getId();
    }

    private Mark mapMark(Map<String, Object> row) {
        return new Mark.Builder()
                .setId((int) row.get("_id"))
                .setActualMark((int) row.get(ACTUAL_MARK.toString()))
                .setComment((String) row.get(COMMENT.toString()))
                .setMarkerId((String) row.get(MARKER_ID.toString()))
                .build();
    }
}
