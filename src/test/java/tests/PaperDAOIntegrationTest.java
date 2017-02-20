package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.PaperDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
public class PaperDAOIntegrationTest {
    @Autowired
    private PaperDAO paperDao;

    @Test
    public void canGetCount() {
        assertEquals(3, paperDao.getCount());
    }

    @Test
    public void canReadPaperById() {
        Assert.assertEquals(TestResources.PAPER_ID_1, paperDao.read(1));
    }
}
