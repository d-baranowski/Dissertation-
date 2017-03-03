package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDayEntry;
import javax.annotation.PostConstruct;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class TestDayEntryDAOIntegrationTest {
    @Autowired
    private ApplicationContext context;
    private TestDayEntryDAO dao;

    @PostConstruct
    public void init() {
        dao = context.getBean(TestDayEntryDAO.class);
    }

    @Test
    public void aCanGetCount() {
        assertEquals(2, dao.getCount());
    }

    /*@Test
    public void bCanCreate() {
        TestDayEntry created = new TestDayEntry.Builder()
                .setId(dao.getCount() + 1)
                .setTestDayId(1)
                .setPaperVersionNo(1)
                .setPaperId(1)
                .setCandidateId(2)
                .setMinutesRemaining(60)
                .setTermsAndConditions(0)
                .build();

        dao.create(created);
        assertEquals(created, dao.read(dao.getCount()));
    }*/

    @Test
    public void cCanLockForMarking() {
        dao.setMarkerId(1, "3ca33b4f-009a-4403-829b-e2d20b3d47c2");
        assertEquals("3ca33b4f-009a-4403-829b-e2d20b3d47c2", dao.getMarkersId(1));
    }

    /*@Test
    public void canRead() {
        TestDayEntry expected = new TestDayEntry.Builder()
                .setId(1)
                .setCandidateId(1)
                .setPaperVersionNo(1)
                .setPaperId(1)
                .setTestDayId(1)
                .setTermsAndConditions(0)
                .setMinutesRemaining(60)
                .build();

        assertEquals(expected, dao.read(1));
    }*/
}
