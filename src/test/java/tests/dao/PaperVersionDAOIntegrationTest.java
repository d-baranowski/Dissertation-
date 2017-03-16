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
import uk.ac.ncl.daniel.baranowski.data.access.PaperVersionDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.PaperVersion;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PaperVersionDAOIntegrationTest {
    @Autowired
    private ApplicationContext context;
    private PaperVersionDAO paperVersionDao;

    @PostConstruct
    public void init() {
        paperVersionDao = context.getBean(PaperVersionDAO.class);
    }

    @Test
    public void canGetCount() {
        assertEquals(3, paperVersionDao.getCount());
    }

    @Test
    public void canReadById() {
        Assert.assertEquals(TestResources.PAPER_VERSION_ID_1_VERNO_1, paperVersionDao.read(1, 1));
    }

 /*   @Test
    public void canReadAll() {
        List<PaperVersion> expected = Arrays.asList(TestResources.PAPER_VERSION_ID_1_VERNO_1, TestResources.PAPER_VERSION_ID_2_VERNO_1);
        assertEquals(expected, paperVersionDao.readAll());
    }*/

    @Test
    public void canGetByPaperId() {
        List<PaperVersion> expected = Arrays.asList(TestResources.PAPER_VERSION_ID_1_VERNO_1);
        assertEquals(expected, paperVersionDao.geByPaperId(1));
    }

    @Test
    public void canGetByAuthorId() {
        List<PaperVersion> expected = Arrays.asList(TestResources.PAPER_VERSION_ID_1_VERNO_1, TestResources.PAPER_VERSION_ID_2_VERNO_1);
        assertEquals(expected, paperVersionDao.getByAuthorId("9f4db0ac-b18a-4777-8b04-b72a0eeccf5d"));
    }
}
