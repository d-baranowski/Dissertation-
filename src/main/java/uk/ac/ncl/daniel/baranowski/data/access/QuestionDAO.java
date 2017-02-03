package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Question;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@DataAccessObject
public class QuestionDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(Question obj) {
        //TODO BEYOND MVP NOSONAR
    }

    public Question read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.QUESTION);
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, objId);
        return mapQuestion(row, getQuestionTypeByQuestionId((String) row.get("questionTypeId")));
    }

    public void update(Question obj) {
        //TODO BEYOND MVP NOSONAR
    }

    public List<Question> readAll() {
        final String sql = String.format("SELECT * FROM %s t", TableNames.QUESTION);
        List<Question> result = new ArrayList<>();

        for (Map<String, Object> row : jdbcTemplate.queryForList(sql)) {
            result.add(mapQuestion(row, getQuestionTypeByQuestionId((String) row.get("questionTypeId"))));
        }

        return result;
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.QUESTION);
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
}
