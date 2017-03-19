package tests.dao;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.TestDayDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
import uk.ac.ncl.daniel.baranowski.data.exceptions.DateFormatException;

import javax.annotation.PostConstruct;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations="classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class TestDayDAOIntegrationTest {
    @Autowired
    private ApplicationContext context;
    private TestDayDAO dao;

    @PostConstruct
    public void init() {
        dao = context.getBean(TestDayDAO.class);
    }

    @Test
    public void aCanGetCount() {
        assertEquals(1, dao.getCount());
    }

    @Test
    public void cCanCreateTestDay() throws DateFormatException {
        TestDay added = new TestDay.Builder()
                .setDate("02/03/2016")
                .setLocation("BJSS Leeds Office")
                .setStartTime("14:00")
                .setEndLocalTime("15:00")
                .setEndTimeWithExtraTime("15:30")
                .setId(dao.getCount() + 1).build();
        dao.create(added);

        assertEquals(added, dao.read(dao.getCount()));
    }
}
