package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Question;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static uk.ac.ncl.daniel.baranowski.data.access.QuestionDAO.ColumnNames.*;
import static uk.ac.ncl.daniel.baranowski.data.access.TableNames.QUESTION;

@DataAccessObject
public class QuestionDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int create(Question obj) {
        Map<String, Object> args = new HashMap<>();

        args.put(LANGUAGE.toString(),  obj.getLanguage());
        args.put(DIFFICULTY.toString(), obj.getDifficulty());
        args.put(REFERENCE_NAME.toString(),  obj.getReferenceName());
        args.put(QUESTION_TYPE_ID.toString(),obj.getType());

        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(QUESTION.toString())
                .usingColumns(
                        LANGUAGE.toString()
                        , DIFFICULTY.toString()
                        , REFERENCE_NAME.toString()
                        , QUESTION_TYPE_ID.toString())
                .usingGeneratedKeyColumns(ID.toString())
                .executeAndReturnKey(args);
        return key.intValue();
    }

    public Question read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", QUESTION);
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, objId);
        return mapQuestion(row, getQuestionTypeByQuestionId((String) row.get("questionTypeId")));
    }

    public void update(Question obj) {
        //TODO BEYOND MVP NOSONAR
    }

    public List<Question> readAll() {
        final String sql = String.format("SELECT * FROM %s t", QUESTION);
        List<Question> result = new ArrayList<>();

        for (Map<String, Object> row : jdbcTemplate.queryForList(sql)) {
            result.add(mapQuestion(row, getQuestionTypeByQuestionId((String) row.get("questionTypeId"))));
        }

        return result;
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", QUESTION);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int getTypeCount() {
        final String sql = String.format("select count(*) from %s", TableNames.QUESTION_TYPE);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private Question mapQuestion(Map<String, Object> row, String type) {
        return new Question.Builder()
                .setId((int) row.get("_id"))
                .setLanguage((String) row.get("language"))
                .setDifficulty((int) row.get("difficulty"))
                .setReferenceName((String) row.get("referenceName"))
                .setType(type)
                .build();
    }

    private String getQuestionTypeByQuestionId(String questionId) {
        final String sql = String.format("select * from %s WHERE referenceName = ?", TableNames.QUESTION_TYPE);
        return (String) jdbcTemplate.queryForMap(sql, questionId).get("referenceName");
    }

    public enum ColumnNames {
        ID("_id"),
        LANGUAGE("language"),
        DIFFICULTY("difficulty"),
        REFERENCE_NAME("referenceName"),
        QUESTION_TYPE_ID("questionTypeId");

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
