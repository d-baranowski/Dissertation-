package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.SectionVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.SectionVersion;
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
        assertEquals(6, dao.getCount());
    }

    @Test
    public void canReadById() {
        Assert.assertEquals(TestResources.SECTION_VERSION_ID_1_VERNO_1, dao.read(1, 1));
    }

    @Test
    public void canReadAll() {
        List<SectionVersion> actual = dao.readAll();
        assertTrue(actual.containsAll(Arrays.asList(TestResources.SECTION_VERSION_ID_1_VERNO_1)) && actual.size() == 6);
    }

    @Test
    public void canGetSectionReferenceNumber() {
        assertEquals(new Integer(1), dao.getSectionReferenceNumber(1, 1, 1, 1));
    }
}
