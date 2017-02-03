package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersionAsset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

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
}
