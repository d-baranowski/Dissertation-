package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.AnswerAsset;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static uk.ac.ncl.daniel.baranowski.data.access.AnswerAssetDAO.ColumnNames.BLOB;
import static uk.ac.ncl.daniel.baranowski.data.access.AnswerAssetDAO.ColumnNames.QUESTION_ID;
import static uk.ac.ncl.daniel.baranowski.data.access.AnswerAssetDAO.ColumnNames.QUESTION_VERSION_NO;
import static uk.ac.ncl.daniel.baranowski.data.access.AnswerAssetDAO.ColumnNames.REFERENCE_NAME;
import static uk.ac.ncl.daniel.baranowski.data.access.AnswerAssetDAO.ColumnNames.TEST_DAY_ENTRY_ID;

@DataAccessObject
public class AnswerAssetDAO {
    private final JdbcTemplate jdbcTemplate;

    //This constructor appears like its never used, but its used for dependency injection using Autowired annotation
    @Autowired
    private AnswerAssetDAO(JdbcTemplate jdbcTemplate) { //NOSONAR
        this.jdbcTemplate = jdbcTemplate;
    }

    public AnswerAsset getByAnswer(int questionId, int questionVersionNumber, int testDayEntryId) {
        final String sql = String.format("SELECT * FROM %s t WHERE questionId = ? AND questionVersionNumber = ? AND testDayEntryId = ?", TableNames.ANSWER_ASSET);
        return mapAnswerAsset(jdbcTemplate.queryForMap(sql, questionId, questionVersionNumber, testDayEntryId), "png");
    }

    public void submit(AnswerAsset asset) {
        if (checkIfExists(asset)) {
            update(asset);
        } else {
            create(asset);
        }
    }

    enum ColumnNames {
        QUESTION_ID("questionId"),
        QUESTION_VERSION_NO("questionVersionNumber"),
        TEST_DAY_ENTRY_ID("testDayEntryId"),
        REFERENCE_NAME("referenceName"),
        BLOB("blob");

        private final String columnName;

        ColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private void create(AnswerAsset asset) {
        jdbcTemplate.update(String.format("INSERT INTO %s(questionId, questionVersionNumber, testDayEntryId, referenceName, _blob) VALUES (?,?,?,?,?);",
                TableNames.ANSWER_ASSET),
                asset.getQuestionId(),
                asset.getQuestionVersionNo(),
                asset.getTestDayEntryId(),
                asset.getReferenceName(),
                asset.getFile());
    }

    private void update(AnswerAsset asset) {
        final String sql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? AND %s = ? AND %s = ?",
                TableNames.ANSWER_ASSET,
                QUESTION_ID,
                QUESTION_VERSION_NO,
                TEST_DAY_ENTRY_ID,
                REFERENCE_NAME,
                BLOB,QUESTION_ID,
                QUESTION_VERSION_NO,
                TEST_DAY_ENTRY_ID
        );
        jdbcTemplate.update(sql,
                asset.getQuestionId(),
                asset.getQuestionVersionNo(),
                asset.getTestDayEntryId(),
                asset.getReferenceName(),
                asset.getFile(),
                asset.getQuestionId(),
                asset.getQuestionVersionNo(),
                asset.getTestDayEntryId()
        );
    }

    private boolean checkIfExists(AnswerAsset asset) {
        final String sql = String.format("SELECT count(*) FROM %s t WHERE t.%s = ? AND t.%s = ? AND t.%s = ?",
                TableNames.ANSWER_ASSET,
                QUESTION_ID,
                QUESTION_VERSION_NO,
                TEST_DAY_ENTRY_ID);

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, asset.getQuestionId(), asset.getQuestionVersionNo(),asset.getTestDayEntryId());

        return count > 0;
    }

    private AnswerAsset mapAnswerAsset(Map<String, Object> row, String fileType) {
        return new AnswerAsset.Builder()
                .setId((int) row.get("_id"))
                .setQuestionId((int) row.get("questionId"))
                .setQuestionVersionNo((int) row.get("questionVersionNumber"))
                .setTestDayEntryId((int) row.get("testDayEntryId"))
                .setReferenceName((String) row.get("referenceName"))
                .setFile((byte[]) row.get("blob"))
                .setFileType(fileType)
                .build();
    }

}
