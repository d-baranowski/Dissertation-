package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.TestDayDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
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
    public void cCanCreateTestDay() {
        TestDay added = new TestDay.Builder().setDate("02/03/2016").setLocation("BJSS Leeds Office").setId(dao.getCount() + 1).build();
        dao.create(added);

        assertEquals(added, dao.read(dao.getCount()));
    }
}
