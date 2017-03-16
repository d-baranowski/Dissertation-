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
import uk.ac.ncl.daniel.baranowski.data.access.SectionDAO;

import javax.annotation.PostConstruct;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations="classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SectionDAOIntegrationTest {
    @Autowired
    private ApplicationContext context;
    private SectionDAO paperSectionDao;

    @PostConstruct
    public void init() {
        paperSectionDao = context.getBean(SectionDAO.class);
    }

    @Test
    public void canGetCount() {
        assertEquals(7, paperSectionDao.getCount());
    }

    @Test
    public void canReadById() {
        Assert.assertEquals(TestResources.SECTION_ID_1, paperSectionDao.read(1));
    }

   /* @Test
    public void canReadAll() {
        List<Section> actual = paperSectionDao.readAll();
        assertTrue(actual.containsAll(Arrays.asList(TestResources.SECTION_ID_1)) && actual.size() == 6);
    }*/
}
