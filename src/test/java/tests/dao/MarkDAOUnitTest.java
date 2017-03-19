package tests.dao;


import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.ac.ncl.daniel.baranowski.data.access.MarkDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Mark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MarkDAOUnitTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    MarkDAO dao;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @After
    public void reset() {
        expectedEx = ExpectedException.none();
    }

    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canRead() {
        final Mark expected = new Mark.Builder()
                .setId(1)
                .setActualMark(10)
                .setComment("Lorem ipsium dolore")
                .setMarkerId("asdasdawdq-sadqwd2-dsddqwdq-31ddqd")
                .build();

        Map<String, Object> row = new HashMap<>();
        row.put("_id", expected.getId());
        row.put("actualMark", expected.getActualMark());
        row.put("comment", expected.getComment());
        row.put("markerId", expected.getMarkerId());

        when(jdbcTemplate.queryForMap(eq("SELECT * FROM Mark t WHERE t._id = ?"), eq(1))).thenReturn(row);
        Mark actual = dao.read(1);
        assertEquals(expected, actual);
    }

    @Test
    public void canSubmitIfExists() {
        final Mark mark = new Mark.Builder()
                .setId(1)
                .setMarkerId("Blablabla")
                .setActualMark(2)
                .setComment("No comment...")
                .build();


        final ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Object> args = ArgumentCaptor.forClass(Object.class);

        final String expectedSql = "UPDATE Mark SET markerId = ?, comment =? , actualMark = ? WHERE _id=?";
        when(jdbcTemplate.queryForObject(eq("SELECT count(*) FROM Mark t WHERE t._id = ? "), eq(Integer.class), eq(mark.getId()))).thenReturn(1);
        when(jdbcTemplate.update(sql.capture(), args.capture())).thenReturn(1);

        dao.submitMark(mark);

        assertEquals(expectedSql, sql.getValue());
        assertEquals(mark.getMarkerId(), args.getAllValues().get(0));
        assertEquals(mark.getComment(), args.getAllValues().get(1));
        assertEquals(mark.getActualMark(), args.getAllValues().get(2));
        assertEquals(mark.getId(), args.getAllValues().get(3));
    }
}
