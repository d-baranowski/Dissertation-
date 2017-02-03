package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Paper;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@DataAccessObject
public class PaperDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PaperDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(Paper paper) {
        //NOSONAR TODO BEYOND MVP
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
}
