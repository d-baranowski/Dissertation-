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
import uk.ac.ncl.daniel.baranowski.data.access.UserDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Role;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tests.TestResources.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations="classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebAppConfiguration
public class UserDAOIntegrationTest {
    @Autowired
    private ApplicationContext context;
    private UserDAO userDao;

    @PostConstruct
    public void init() {
        userDao = context.getBean(UserDAO.class);
    }

    @Test
    public void canGetUserCount() {
        assertEquals(9, userDao.getCount());
    }

    @Test
    public void canGetAllUsers() {
        List<User> actual = userDao.getAllUsers();
        assertTrue(actual.containsAll(Arrays.asList(USER_AUTHOR, USER_ADMIN, USER_MARKER, USER_ALL)));
    }


    @Test
    public void canGetAnUserById() {
        User user = userDao.readUser("3ca33b4f-009a-4403-829b-e2d20b3d47c2");
        assertEquals(USER_MARKER, user);
    }

    @Test
    public void canGetRolesCount() {
        assertEquals(4, userDao.getRolesCount());
    }

    @Test
    public void canGetAllRoles() {
        List<Role> actual = userDao.getAllRoles();
        assertTrue(actual.containsAll(Arrays.asList(ROLE_ADMIN, ROLE_MARKER, ROLE_AUTHOR, ROLE_MODULE_LEADER)));
    }
}
