package uk.ac.ncl.daniel.baranowski.data.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Module;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static uk.ac.ncl.daniel.baranowski.data.access.ModuleDAO.ColumnNames.MODULE_CODE;
import static uk.ac.ncl.daniel.baranowski.data.access.ModuleDAO.ColumnNames.REFERENCE_NAME;

@DataAccessObject
public class ModuleDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ModuleDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Module read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.MODULE);
        return mapModule(jdbcTemplate.queryForMap(sql, objId));
    }

    public int getCount()  {
        final String sql = String.format("select count(*) from %s", TableNames.MODULE);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Module> readAll() {
        final List<Module> result = new ArrayList<>();
        final String sql = String.format("SELECT * FROM %s t", TableNames.MODULE);
        jdbcTemplate.queryForList(sql).forEach(row -> result.add(mapModule(row)));
        return result;
    }

    public List<Module> readAllForModuleLeader(String leaderId) {
        final List<Module> result = new ArrayList<>();
        final String sql = String.format("SELECT m.`_id`," +
                " m.moduleCode," +
                " m.referenceName FROM %s m INNER JOIN %s ml ON m.`_id` = ml.moduleId AND ml.userId = ?",
                TableNames.MODULE,
                TableNames.MODULE_LEADER);

        jdbcTemplate.queryForList(sql, leaderId).forEach(row -> result.add(mapModule(row)));
        return result;
    }

    public boolean isModuleLeader(int moduleId, String userId) {
        final String sql = String.format(
                "SELECT count(*) > 0 FROM %s ml WHERE ml.moduleId = ? AND ml.userId = ?",
                TableNames.MODULE_LEADER);
        boolean result;

        try {
            result = jdbcTemplate.queryForObject(sql, Boolean.class, moduleId, userId);
        } catch (EmptyResultDataAccessException e) {
            result = false;
        }

        return result;
    }

    public enum ColumnNames {
        ID("_id"),
        REFERENCE_NAME("referenceName"),
        MODULE_CODE("moduleCode");

        private final String columnName;

        ColumnNames(String tableName) {
            this.columnName = tableName;
        }

        @Override
        public String toString() {
            return columnName;
        }
    }

    private Module mapModule(Map<String, Object> row) {
        return new Module.Builder()
                .setId((int) row.get("_id"))
                .setReferenceName((String) row.get(REFERENCE_NAME.columnName))
                .setModuleCode((String) row.get(MODULE_CODE.columnName))
                .build();
    }

}
