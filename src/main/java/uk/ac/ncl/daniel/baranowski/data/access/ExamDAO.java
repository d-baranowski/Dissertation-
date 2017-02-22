package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Exam;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.HashMap;
import java.util.Map;

import static uk.ac.ncl.daniel.baranowski.data.access.ExamDAO.ColumnNames.*;

@DataAccessObject
public class ExamDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ExamDAO(JdbcTemplate jdbcTemplate) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = jdbcTemplate;
    }

    public Exam read(final int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.EXAM);
        return mapExam(jdbcTemplate.queryForMap(sql, objId));
    }

    public int create(final Exam obj) {
        Map<String, Object> args = new HashMap<>();
        args.put(TEST_PAPER_VERSION_NO.toString(), obj.getPaperVersionNo());
        args.put(TEST_PAPER_ID.toString(), obj.getPaperId());
        args.put(STATUS.toString(), obj.getStatus());
        args.put(TEST_DAY_ID.toString(), obj.getTestDayId());
        args.put(TERMS_AND_CONDITIONS_ID.toString(), obj.getTermsAndConditionsId());
        args.put(MODULE_ID.toString(), obj.getModuleId());


        Number key = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(TableNames.EXAM.toString())
                .usingColumns(
                        MODULE_ID.columnName,
                        TEST_DAY_ID.columnName,
                        TEST_PAPER_VERSION_NO.columnName,
                        TEST_PAPER_ID.columnName,
                        STATUS.columnName,
                        TERMS_AND_CONDITIONS_ID.columnName
                )
                .usingGeneratedKeyColumns("_id")
                .executeAndReturnKey(args);
        return key.intValue();
    }

    private Exam mapExam(Map<String, Object> row) {
        return new Exam.Builder()
                .setModuleId((int) row.get("moduleId"))
                .setId((int) row.get("_id"))
                .setTestDayId((int) row.get("testDayId"))
                .setPaperVersionNo((int) row.get("testPaperVersionNo"))
                .setPaperId((int) row.get("testPaperId"))
                .setStatus(ExamStatus.getByName((String) row.get("status")))
                .setTermsAndConditionsId((int) row.get("termsAndConditionsId"))
                .build();
    }

    public enum ColumnNames {
        ID("_id"),
        TEST_PAPER_VERSION_NO("testPaperVersionNo"),
        TEST_PAPER_ID("testPaperId"),
        TEST_DAY_ID("testDayId"),
        STATUS("status"),
        MODULE_ID("moduleId"),
        TERMS_AND_CONDITIONS_ID("termsAndConditionsId");

        private final String columnName;

        ColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }
}
