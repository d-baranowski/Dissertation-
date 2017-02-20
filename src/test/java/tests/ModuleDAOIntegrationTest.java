package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.access.ModuleDAO;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
public class ModuleDAOIntegrationTest {
    @Autowired
    private ModuleDAO moduleDAO;

    @Test
    public void canGetCount() {
        assertEquals(10, moduleDAO.getCount());
    }

    @Test
    public void canReadModuleById() {
        Assert.assertEquals(TestResources.MODULE_ONE, moduleDAO.read(1));
    }

    @Test
    public void canCheckIfUserIsModuleLeaderForModule() {
        Assert.assertTrue(moduleDAO.isModuleLeader(1,"9f4db0ac-b18a-4777-8b04-b72a0eeccf5d"));
    }

    @Test
    public void canCheckIfUserIsNotAModuleLeaderForModule() {
        Assert.assertFalse(moduleDAO.isModuleLeader(1,"95818d99-492d-4c50-80b8-abae310bd2f3"));
    }

    @Test
    public void canGetFalseIfUserDoesNotExist() {
        Assert.assertFalse(moduleDAO.isModuleLeader(1, "I do not exist"));
    }
}
