package tests.repo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.common.enums.Roles;
import uk.ac.ncl.daniel.baranowski.data.UserRepo;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.models.UserModel;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static uk.ac.ncl.daniel.baranowski.data.mappers.UserModelMapper.mapUserModelFrom;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations="classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserRepoIntegrationTest {

    @Autowired
    UserRepo userRepo;

    @Test
    public void canRetrieveUser() {
        User user = new User.Builder()
                .setId("3ca33b4f-009a-4403-829b-e2d20b3d47c2")
                .setLogin("sampleMarker")
                .setPassword("pass")
                .setName("Bob")
                .setSurname("Smith").build();

        UserModel fetchUser = mapUserModelFrom(user, Arrays.asList(Roles.MARKER));

        UserModel returnedUser = userRepo.getUserByLogin("sampleMarker");

        assertTrue(returnedUser.equals(fetchUser));
    }

    @Test
    public void responseWhenUserDoesntExistTest() {
        UserModel returnedUser = userRepo.getUserByLogin("madeUpUser");
        assertTrue(returnedUser == null);
    }
}
