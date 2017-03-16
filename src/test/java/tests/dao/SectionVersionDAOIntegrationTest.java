package tests.dao;

import org.junit.Assert;
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
import tests.TestResources;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.SectionVersionDAO;

import javax.annotation.PostConstruct;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations="classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SectionVersionDAOIntegrationTest {
    @Autowired
    private ApplicationContext context;
    private SectionVersionDAO dao;

    @PostConstruct
    public void init() {
        dao = context.getBean(SectionVersionDAO.class);
    }

    @Test
    public void canGetCount() {
        assertEquals(7, dao.getCount());
    }

    @Test
    public void canReadById() {
        Assert.assertEquals(TestResources.SECTION_VERSION_ID_1_VERNO_1, dao.read(1, 1));
    }

   /* @Test
    public void canReadAll() {
        List<SectionVersion> actual = dao.readAll();
        assertTrue(actual.containsAll(Arrays.asList(TestResources.SECTION_VERSION_ID_1_VERNO_1)) && actual.size() == 6);
    }*/

    @Test
    public void canGetSectionReferenceNumber() {
        assertEquals(new Integer(1), dao.getSectionReferenceNumber(1, 1, 1, 1));
    }
}
