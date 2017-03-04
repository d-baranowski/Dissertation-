package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.SectionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Section;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
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
