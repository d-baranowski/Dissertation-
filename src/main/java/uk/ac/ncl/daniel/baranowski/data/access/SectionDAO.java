package uk.ac.ncl.daniel.baranowski.data.access;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Section;
import uk.ac.ncl.daniel.baranowski.data.annotations.DataAccessObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@DataAccessObject
public class SectionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SectionDAO(DataSource dataSource) { //NOSONAR Its private to limit construction to dependency injection
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(Section section) {
        //TODO BEYOND MVP NOSONAR
    }

    public Section read(int objId) {
        final String sql = String.format("SELECT * FROM %s t WHERE t._id = ?", TableNames.TEST_PAPER_SECTION);
        return mapPaperSection(jdbcTemplate.queryForMap(sql, objId));
    }

    public void update(Section section) {
        //TODO BEYOND MVP NOSONAR
    }

    public int getCount() {
        final String sql = String.format("select count(*) from %s", TableNames.TEST_PAPER_SECTION);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<Section> readAll() {
        final String sql = String.format("SELECT * FROM %s", TableNames.TEST_PAPER_SECTION);

        List<Section> result = new ArrayList<>();
        for (Map<String, Object> row : jdbcTemplate.queryForList(sql)) {
            result.add(mapPaperSection(row));
        }

        return result;
    }

    private Section mapPaperSection(Map<String, Object> row) {
        return new Section.Builder()
                .setId((int) row.get("_id"))
                .setReferenceName((String) row.get("referenceName"))
                .build();
    }
}
