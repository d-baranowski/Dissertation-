package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersionAsset;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.ncl.daniel.baranowski.data.access.AnswerAssetDAO.ColumnNames.BLOB;
import static uk.ac.ncl.daniel.baranowski.data.access.QuestionVersionAssetDAO.ColumnNames.*;
import static uk.ac.ncl.daniel.baranowski.data.access.TableNames.QUESTION_VERSION_ASSET;

@DataAccessObject
public class QuestionVersionAssetDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private QuestionVersionAssetDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<QuestionVersionAsset> getByQuestionVersion(int questionId, int versionNo) {
        final String sql = "SELECT * FROM QuestionVersionAsset t WHERE t.questionId = ? AND t.questionVersionNumber = ?";

        List<QuestionVersionAsset> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql, questionId, versionNo)) {
            result.add(mapQuestionVersionAsset(row));
        }

        return result;
    }

    private static QuestionVersionAsset mapQuestionVersionAsset(Map<String, Object> row) {
        return new QuestionVersionAsset.Builder()
                .setId((int) row.get("_id"))
                .setQuestionId((int) row.get("questionId"))
                .setQuestionVersionNo((int) row.get("questionVersionNumber"))
                .setReferenceName((String) row.get("referenceName"))
                .setFile((byte[]) row.get("blob"))
                .setFileType((String) row.get("blobType")).build();
    }

    public int create(QuestionVersionAsset obj) {
        Map<String, Object> args = new HashMap<>();
        args.put(ID.toString(), obj.getId());
        args.put(QUESTION_ID.toString(), obj.getQuestionId());
        args.put(QUESTION_VERSION_NO.toString(), obj.getQuestionVersionNo());
        args.put(REFERENCE_NAME.toString(),  obj.getReferenceName());
        args.put(BLOB.toString(), obj.getFile());
        args.put(BLOB_TYPE.toString(),  obj.getFileType());

        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(QUESTION_VERSION_ASSET.toString())
                .usingColumns(
                        ID.toString()
                        , QUESTION_ID.toString()
                        , QUESTION_VERSION_NO.toString()
                        , REFERENCE_NAME.toString()
                        , BLOB.toString()
                        , BLOB_TYPE.toString())
                .usingGeneratedKeyColumns("_id")
                .executeAndReturnKey(args);
        return key.intValue();
    }

    public enum ColumnNames {
        ID("_id"),
        QUESTION_ID("questionId"),
        QUESTION_VERSION_NO("questionVersionNumber"),
        REFERENCE_NAME("referenceName"),
        BLOB("blob"),
        BLOB_TYPE("blobType");

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
