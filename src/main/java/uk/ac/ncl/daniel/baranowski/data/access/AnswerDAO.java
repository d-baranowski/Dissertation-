package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Answer;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@DataAccessObject
public class AnswerDAO {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger LOGGER = Logger.getLogger(AnswerDAO.class.getName());

    @Autowired
    private AnswerDAO(JdbcTemplate jdbcTemplate) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = jdbcTemplate;
    }

    public void submitAnswer(Answer obj) {
        final boolean exists = checkIfExists(obj);

        if (!exists) {
            create(obj);
        } else {
            update(obj);
        }
    }

    public void markAnswer(int attemptId, int questionId, int questionVersionNo, int markId) {
        final Answer answer = new Answer.Builder()
                .setMarkId(markId)
                .setQuesionVersionNo(questionVersionNo)
                .setQuesionId(questionId)
                .setTestDayEntryId(attemptId)
                .build();

        final boolean exists = checkIfExists(answer);

        if (exists == false) {
            create(answer);
        }

        final int rows = jdbcTemplate.update(String.format("UPDATE %s SET markId = ? WHERE questionVersionNumber = ? AND questionId = ? AND testDayEntryId = ?;", TableNames.ANSWER),
                markId, questionVersionNo, questionId, attemptId);
    }

    public Answer read(int questionVersionNo, int questionId, int testDayEntryId) {
        final String sql = String.format("SELECT * FROM %s t WHERE questionVersionNumber = ? AND questionId = ? AND testDayEntryId = ?", TableNames.ANSWER);
        return mapAnswer(jdbcTemplate.queryForMap(sql, questionVersionNo, questionId, testDayEntryId));
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.ANSWER);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public Answer getByQuestion(int testDayEntryId, int questionId, int questionVersionNo) {
        final String sql = String.format(
                "SELECT * FROM %s t WHERE t.testDayEntryId = ? " +
                        "AND t.questionId = ? AND t.questionVersionNumber = ?", TableNames.ANSWER);
        try {
            return mapAnswer(jdbcTemplate.queryForMap(sql, testDayEntryId, questionId, questionVersionNo));
        } catch (EmptyResultDataAccessException e) {
            LOGGER.log(Level.FINE, String.format("There are no answers for question: %s version: %s for test day entry: %s",
                    questionId, questionVersionNo, testDayEntryId), e);
            return null;
        }
    }

    private boolean checkIfExists(Answer answer) {
        final String sql = String.format(
                "SELECT count(*) FROM %s t WHERE t.testDayEntryId = ? " +
                        "AND t.questionId = ? AND t.questionVersionNumber = ?", TableNames.ANSWER);

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, answer.getTestDayEntryId(), answer.getQuestionId(), answer.getQuestionVersionNo());

        return count > 0;
    }

    private void create(Answer obj) {
        jdbcTemplate.update(String.format("INSERT INTO %s(questionVersionNumber, questionId, testDayEntryId, text) VALUES (?,?,?,?);", TableNames.ANSWER),
                obj.getQuestionVersionNo(), obj.getQuestionId(), obj.getTestDayEntryId(), obj.getText());
    }

    private void update(Answer obj) {
        if (obj.getMarkId() == null) {
            jdbcTemplate.update(String.format("UPDATE %s SET text = ? WHERE questionVersionNumber = ? AND questionId = ? AND testDayEntryId = ?;", TableNames.ANSWER),
                    obj.getText(), obj.getQuestionVersionNo(), obj.getQuestionId(), obj.getTestDayEntryId());
        } else {
            jdbcTemplate.update(String.format("UPDATE %s SET text = ?, markId = ? WHERE questionVersionNumber = ? AND questionId = ? AND testDayEntryId = ?;", TableNames.ANSWER),
                    obj.getText(), obj.getMarkId(), obj.getQuestionVersionNo(), obj.getQuestionId(), obj.getTestDayEntryId());
        }
    }

    private Answer mapAnswer(Map<String, Object> row) {
        return new Answer.Builder()
                .setQuesionVersionNo((int) row.get("questionVersionNumber"))
                .setQuesionId((int) row.get("questionId"))
                .setTestDayEntryId((int) row.get("testDayEntryId"))
                .setMarkId((Integer) row.get("markId"))
                .setText((String) row.get("text")).build();
    }
}
